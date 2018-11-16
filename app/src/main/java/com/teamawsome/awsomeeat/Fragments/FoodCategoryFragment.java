package com.teamawsome.awsomeeat.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamawsome.awsomeeat.Common.Common;
import com.teamawsome.awsomeeat.Model.Category;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.ViewHolder.FoodListViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class FoodCategoryFragment extends Fragment {

    private List<Category> foodCategoryList = new ArrayList<>();
   // private CategoryListRecyclerViewAdapter adapter;
    FirebaseRecyclerAdapter<Category,FoodListViewHolder> adapter;
    //private FirebaseFirestore db;
    private FirebaseDatabase database;
    private DatabaseReference category;
    private RecyclerView recyclerView;

    public FoodCategoryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_category, container, false);

        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");


        recyclerView = view.findViewById(R.id.recycler_menu);

        //TODO Swich to start using own adapterClass instead of Firebase when swiching database to Forebase.
        // adapter = new CategoryListRecyclerViewAdapter(foodCategoryList);
        //recyclerView.setAdapter(adapter);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(Common.isNetworkAvailable(getContext()))
            loadMenu();
        else
        {
            Toast.makeText(getActivity(), "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void loadMenu() {

        adapter = new FirebaseRecyclerAdapter<Category, FoodListViewHolder>(Category.class,R.layout.menu_item,FoodListViewHolder.class,category) {
            @Override
            protected void populateViewHolder(FoodListViewHolder viewHolder, Category model, int position) {

                viewHolder.itemId = adapter.getRef(position).getKey();
                viewHolder.setData(model);
                final Category clickItem = model;
            }
        };
        recyclerView.setAdapter(adapter);
    }

}