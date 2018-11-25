package com.teamawsome.awsomeeat.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.teamawsome.awsomeeat.Adapters.FoodListRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Adapters.RestaurantRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.idHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

//TODO Remove from project....

public class MenuListFragment extends Fragment {
    private List<Food> itemList = new ArrayList<>();
    private FoodListRecyclerViewAdapter adapter;
    private FirebaseFirestore db;
    String CategoryId;
    String restaurantId;

    public MenuListFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.menufragment_layout, container, false);
        adapter = new FoodListRecyclerViewAdapter(itemList);
        db= FirebaseFirestore.getInstance();
        if(idHolder.getCategoryId() != null) {
            CategoryId = idHolder.getCategoryId();
        }

        if(idHolder.getRestaurantId() != null) {
            restaurantId = idHolder.getRestaurantId();
        }

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.menulist);
        recyclerView.setAdapter(adapter);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
;


      /*db.collection("restaurants")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                restaurantsid.add(id);
                            }
                        } else {
                            Log.d("tagggging", "Error getting documents: ", task.getException());
                        }
                    }
                });*/


       db.collection("Foods").whereArrayContains("restaurantId", restaurantId).whereEqualTo("Category", CategoryId)
               .addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
            if (e != null){
                return;
            }
            for (DocumentChange dc: queryDocumentSnapshots.getDocumentChanges()){
                if (dc.getType() == DocumentChange.Type.ADDED){

                    String id = dc.getDocument().getId();
                    Food food = dc.getDocument().toObject(Food.class);
                    food.setId(id);
                    adapter.addItem(food);
                }
                else if(dc.getType() == DocumentChange.Type.MODIFIED){

                    String id = dc.getDocument().getId();
                    Food food = dc.getDocument().toObject(Food.class);
                    adapter.modifyItem(id, food);
                }

                else if (dc.getType() == DocumentChange.Type.REMOVED){
                    String id = dc.getDocument().getId();
                    adapter.removeMenuItem(id);
                }
            }
        }
    });

     /* db.collection("Foods").whereArrayContains("restaurantId", CategoryId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Food food = document.toObject(Food.class);
                                adapter.addItem(food);
                            }
                        } else {

                        }
                    }
                });*/

}
}
