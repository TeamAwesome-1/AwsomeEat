package com.teamawsome.awsomeeat.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.teamawsome.awsomeeat.Adapters.CategoryListRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Admin.FirestoreMain;
import com.teamawsome.awsomeeat.Common.Common;
import com.teamawsome.awsomeeat.Model.Category;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.idHolder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

//Displays a list of food Categories.
public class FoodCategoryFragment extends Fragment {

    private List<Category> foodCategoryList = new ArrayList<>();
    private CategoryListRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private String restaurantId;
    private FirestoreMain firestoreMain = FirestoreMain.getInstance();

    public FoodCategoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_category, container, false);

        //Get the id for the restaurant user pressed
        if(idHolder.getRestaurantId() != null) {
            restaurantId= idHolder.getRestaurantId();
        }

        recyclerView = view.findViewById(R.id.recycler_menu);
        adapter = new CategoryListRecyclerViewAdapter(foodCategoryList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Load categoryList from database for the restaurant user pressed in previous fragment.

        firestoreMain.getCategoriesForRestaurant(adapter, restaurantId);
    }

    @Override
    public void onStop(){
        super.onStop();
        //Deletes local list
        adapter.clearList();
        firestoreMain.detachSnapShotListener();
    }

}
