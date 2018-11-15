package com.teamawsome.awsomeeat.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.teamawsome.awsomeeat.Common.Common;
import com.teamawsome.awsomeeat.FoodDetail;
import com.teamawsome.awsomeeat.FoodList;
import com.teamawsome.awsomeeat.Interface.ItemClickListener;
import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.ViewHolder.FoodViewHolder;

import javax.annotation.Nullable;


public class FoodListFragment extends Fragment {


   RecyclerView recyclerView;
   RecyclerView.LayoutManager layoutManager;
   FirebaseDatabase database;
   DatabaseReference foodList;
   String categoryId="";

    private static final String TAG = "Logging Example";

    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;


    public FoodListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_food_list, container, false);

        //Get info from the previous fragment- now FoodCategoryFragment
        categoryId = getArguments().get("CategoryId").toString();

        //Firebase_init
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(Common.isNetworkAvailable(getContext()))
            loadListFood(categoryId);
        else
        {
            Toast.makeText(getContext(), "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
            return;
        }

        loadListFood(categoryId);


    }


    private void loadListFood(String categoryId) {
        //TODO Change to firebase-database and make an adapter for FoodList
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("MenuId").equalTo(categoryId)  //similar to select * from food where MenuId=
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                Log.d(TAG,"picaasso" );

                //TODO OBS! REPLACED OLD WAY OF SHOWING DATA
                viewHolder.setData(model);
                final Food local =  model;
            }
        };
        //set adapter
        recyclerView.setAdapter(adapter);
    }


}




