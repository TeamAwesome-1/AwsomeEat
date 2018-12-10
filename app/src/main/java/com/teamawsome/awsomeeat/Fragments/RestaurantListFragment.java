package com.teamawsome.awsomeeat.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.teamawsome.awsomeeat.Admin.AdminMain;
import com.teamawsome.awsomeeat.Admin.FirestoreMain;
import com.teamawsome.awsomeeat.AwsomeEatActivity;
import com.teamawsome.awsomeeat.Database.Authentication;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.Model.Restaurant;
import com.teamawsome.awsomeeat.Adapters.RestaurantRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

//Shows a list with all restaurants
public class RestaurantListFragment extends Fragment {

    private List<Restaurant> itemList = new ArrayList<>();
    private RestaurantRecyclerViewAdapter adapter;
    private FirestoreMain firestoreMain= FirestoreMain.getInstance();
    private static Authentication authentication = Authentication.getInstance();

    public RestaurantListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        adapter = new RestaurantRecyclerViewAdapter(itemList);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setAdapter(adapter);
        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        //Load Restaurants from database
            firestoreMain.getRestaurantList(adapter);

        //specifies what happens when button is clicked - adminView is opened.
        // getActivity().findViewById(R.id.Button).setOnClickListener((View view) -> {
        // Intent intent = new Intent(getContext(), AdminMain.class);
        // startActivity(intent);

        //});

    }

    public void onStop(){
        super.onStop();
        //Deletes local list
        adapter.clearList();
        firestoreMain.detachSnapShotListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        authentication.checkAuthState(getActivity());
    }


}

