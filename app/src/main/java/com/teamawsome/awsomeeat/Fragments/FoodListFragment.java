package com.teamawsome.awsomeeat.Fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.teamawsome.awsomeeat.Adapters.FoodListRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Admin.FirestoreMain;
import com.teamawsome.awsomeeat.Common.Common;
import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.idHolder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

//Displays a foodlist
public class FoodListFragment extends Fragment {


    private List<Food> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private String categoryId;
    private String restaurantId;
    private FirestoreMain firestoreMain = FirestoreMain.getInstance();

    private static final String TAG = "Logging Example";
    FoodListRecyclerViewAdapter adapter;



    public FoodListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_food_list, container, false);

        //init the adapter
        adapter = new FoodListRecyclerViewAdapter(itemList);

        //Get info about which category user pressed and for which restaurant
        if(idHolder.getCategoryId() != null) {
            categoryId = idHolder.getCategoryId();
        }
        if(idHolder.getRestaurantId() != null) {
            restaurantId = idHolder.getRestaurantId();
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_food);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

            //Load Foodlist for a specific category to the choosen restaurant
            if(restaurantId != null && categoryId != null) {

              firestoreMain.getMenuForRestaurantCategory(adapter, restaurantId, categoryId);

            }else if(categoryId == null) {
              firestoreMain.getRestaurantMenu(adapter, restaurantId);
              //TODO ta bort /Sandra
              Toast.makeText(getContext(), "Whole foodlist for restaurant is displayed", Toast.LENGTH_SHORT).show();

            }
        }



    @Override
    public void onStop(){
        super.onStop();
        //Deletes local list
        adapter.clearList();
        firestoreMain.detachSnapShotListener();
    }



}






