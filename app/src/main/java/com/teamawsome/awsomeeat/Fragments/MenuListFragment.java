package com.teamawsome.awsomeeat.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.teamawsome.awsomeeat.MenuList.MenuListRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.R;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;


public class MenuListFragment extends Fragment {

    private List<Food> itemList = new ArrayList<>();
    private MenuListRecyclerViewAdapter adapter;
    private FirebaseFirestore db;

    public MenuListFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.menufragment_layout, container, false);
        adapter = new MenuListRecyclerViewAdapter(itemList);
        db= FirebaseFirestore.getInstance();

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.menulist);
        recyclerView.setAdapter(adapter);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);



        db.collection("Menu").addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
            if (e != null){
                return;
            }
            for (DocumentChange dc: queryDocumentSnapshots.getDocumentChanges()){
                if (dc.getType() == DocumentChange.Type.ADDED){
                    String id = dc.getDocument().getId();
                    Food food = dc.getDocument().toObject(Food.class);
                    adapter.addItem(food);
                }
                else if (dc.getType() == DocumentChange.Type.REMOVED){
                    String id = dc.getDocument().getId();
                    //adapter.removeMenu(id);
                }
            }

        }
    });
        db.collection("Menu")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {

                            }
                        } else {

                        }
                    }
                });

}
}
