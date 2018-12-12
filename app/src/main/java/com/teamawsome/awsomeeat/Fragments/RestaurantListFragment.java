package com.teamawsome.awsomeeat.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawsome.awsomeeat.Database.FirestoreMain;
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
        RecyclerView recyclerView = view.findViewById(R.id.list);
        authentication.checkAdminState();
        recyclerView.setAdapter(adapter);
        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        firestoreMain.getRestaurantList(adapter);

    }

    public void onStop(){
        super.onStop();
        //Deletes local list
        adapter.clearList();
        firestoreMain.detachListenerForRestaurantList();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Load Restaurants from database
        authentication.checkAuthState(getActivity());
    }


}

