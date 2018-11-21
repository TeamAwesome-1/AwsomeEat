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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.teamawsome.awsomeeat.Adapters.CategoryListRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Common.Common;
import com.teamawsome.awsomeeat.Model.Category;
import com.teamawsome.awsomeeat.Model.Category;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.ViewHolder.FoodListViewHolder;
import com.teamawsome.idHolder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class FoodCategoryFragment extends Fragment {

    private List<Category> foodCategoryList = new ArrayList<>();
    private CategoryListRecyclerViewAdapter adapter;
    //FirebaseRecyclerAdapter<Category,FoodListViewHolder> adapter;
    private FirebaseFirestore db;
    //private FirebaseDatabase database;
    private RecyclerView recyclerView;
    private String restaurantId;

    public FoodCategoryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_category, container, false);

        //Get the id for the restaurant user pressed
        if(idHolder.restaurantId != null) {
            restaurantId= idHolder.restaurantId;
        }


        db = FirebaseFirestore.getInstance();
        //category = database.getReference("Category");
        recyclerView = view.findViewById(R.id.recycler_menu);

        adapter = new CategoryListRecyclerViewAdapter(foodCategoryList);
        recyclerView.setAdapter(adapter);
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

        if(foodCategoryList.size()==0) {
            db.collection("Categories").whereArrayContains("restaurantId", restaurantId)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                return;
                            }
                            for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                                if (dc.getType() == DocumentChange.Type.ADDED) {

                                    String id = dc.getDocument().getId();
                                    Category category = dc.getDocument().toObject(Category.class);
                                    category.setId(id);
                                    adapter.addCategoryItem(category);
                                } else if (dc.getType() == DocumentChange.Type.MODIFIED) {

                                    //TODO make method for modifying category
                                    String id = dc.getDocument().getId();
                                    Category category = dc.getDocument().toObject(Category.class);
                                    //adapter.modifyItem(id, category);
                                } else if (dc.getType() == DocumentChange.Type.REMOVED) {
                                    String id = dc.getDocument().getId();
                                    //TODO make method for removing category
                                    //adapter.removeItem(id);
                                }
                            }
                        }
                    });

        }

        /* adapter = new FirebaseRecyclerAdapter<Category, FoodListViewHolder>(Category.class,R.layout.menu_item,FoodListViewHolder.class, ) {
            @Override
            protected void populateViewHolder(FoodListViewHolder viewHolder, Category model, int position) {

                viewHolder.itemId = adapter.getRef(position).getKey();
                viewHolder.setData(model);
                final Category clickItem = model;
            }
        };
        recyclerView.setAdapter(adapter);*/
    }

}
