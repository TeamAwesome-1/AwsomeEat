package com.teamawsome.awsomeeat.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.Model.Restaurant;
import com.teamawsome.awsomeeat.Adapters.RestaurantRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;


public class RestaurantListFragment extends Fragment {

    private List<Restaurant> itemList = new ArrayList<>();
    private RestaurantRecyclerViewAdapter adapter;
    private FirebaseFirestore db;


    public RestaurantListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        adapter = new RestaurantRecyclerViewAdapter(itemList);
        db= FirebaseFirestore.getInstance();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);

        recyclerView.setAdapter(adapter);

        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
      super.onActivityCreated(savedInstanceState);

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

        getActivity().findViewById(R.id.Button).setOnClickListener((View view) -> {
            Restaurant r = new Restaurant("Restaurant ", "Gamla sjövägen","https://media-cdn.tripadvisor.com/media/photo-s/02/54/b7/ff/wrights-restaurant.jpg");

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

