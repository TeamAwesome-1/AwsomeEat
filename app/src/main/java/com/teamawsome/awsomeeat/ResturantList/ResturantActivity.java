package com.teamawsome.awsomeeat.ResturantList;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.teamawsome.awsomeeat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class ResturantActivity extends AppCompatActivity implements View.OnClickListener {
    public List<Restaurant> itemList = new ArrayList<>();
    public Map<String, Restaurant> restaurants = new HashMap<>();
    public RestaurantRecyclerViewAdapter adapter;
    public FirebaseFirestore db;
    private FirestoreMain firestoreMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant);
        db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.list);
        adapter = new RestaurantRecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(adapter);
        firestoreMain = FirestoreMain.getInstance();
        FloatingActionButton button = findViewById(R.id.Button);
        button.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View view) {
        firestoreMain.addRestaurant(db, adapter);
    }









                    /*.addOnCanceledListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                        }
                     })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                 }});*/







        public void clearRestaurants () {
            findViewById(R.id.removeRestaurantsButton).setOnClickListener((View view) -> {
                firestoreMain.clearRestaurants(db, adapter);
            });

        }
}



