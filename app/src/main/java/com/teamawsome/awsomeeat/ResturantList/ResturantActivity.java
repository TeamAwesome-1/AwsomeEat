package com.teamawsome.awsomeeat.ResturantList;

import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.teamawsome.MenuList.MenuListFragment;
import com.teamawsome.awsomeeat.FoodList;
import com.teamawsome.awsomeeat.Interface.ItemClickListener;
import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.ViewHolder.FoodViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class ResturantActivity extends AppCompatActivity {
    List<Restaurant> itemList = new ArrayList<>();
    Map<String, Restaurant> restaurants = new HashMap<>();
    private RestaurantRecyclerViewAdapter adapter;
    FirebaseFirestore db;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant);
        db= FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.list);
        adapter = new RestaurantRecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(adapter);

        db.collection("restaurants").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null){
                    return;
                }

                for (DocumentChange dc: queryDocumentSnapshots.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){

                        String id = dc.getDocument().getId();
                        Restaurant restaurant = dc.getDocument().toObject(Restaurant.class);
                        restaurant.id = id;
                        adapter.addItem(restaurant);
                    }
                        else if (dc.getType() == DocumentChange.Type.REMOVED){
                        String id = dc.getDocument().getId();
                        adapter.removeResturant(id);
                    }
                }

                }
                });

        findViewById(R.id.Button).setOnClickListener((View view) -> {
            count++;
            Restaurant r = new Restaurant("Restaurant "+ count, "Gamla sjövägen","http://medifoods.my/images/menu/p1_ginger_pao.jpg");

            db.collection("restaurants")
                    .add(r);
                    /*.addOnCanceledListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                        }
                     })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                 }});*/
        });


    }

}
