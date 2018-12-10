package com.teamawsome.awsomeeat.Fragments;



import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawsome.awsomeeat.Adapters.FoodListRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Admin.FirestoreMain;
import com.teamawsome.awsomeeat.Database.Authentication;
import com.teamawsome.awsomeeat.EventHandler;
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
    private FloatingActionButton category4Button;
    private FloatingActionButton category2Button;
    private FloatingActionButton category3Button, wholeMenuButton;
    private FloatingActionButton category1Button;
    private FloatingActionButton category5Button;
    private FloatingActionButton addNewDishButton;
    private FirestoreMain firestoreMain = FirestoreMain.getInstance();
    private EventHandler eventHandler = EventHandler.getInstance();
    private List <FloatingActionButton> floatingActionButtons;

    private Authentication auth = Authentication.getInstance();
    private static final String TAG = "Logging Example";
    FoodListRecyclerViewAdapter adapter;



    public FoodListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_food_list, container, false);


        category4Button = view.findViewById(R.id.category4_button);
        category2Button = view.findViewById(R.id.category2_button);
        category1Button = view.findViewById(R.id.category1_button);
        category3Button = view.findViewById(R.id.category3_button);
        category5Button = view.findViewById(R.id.category5_button);

        floatingActionButtons = new ArrayList<>();
        floatingActionButtons.add(category1Button);
        floatingActionButtons.add(category2Button);
        floatingActionButtons.add(category3Button);
        floatingActionButtons.add(category4Button);
        floatingActionButtons.add(category5Button);

        wholeMenuButton = view.findViewById(R.id.wholeMenuButton);
        addNewDishButton = view.findViewById(R.id.addnewdish_button);
        //init the adapter
        adapter = new FoodListRecyclerViewAdapter(itemList, floatingActionButtons);
        if(idHolder.getRestaurantId() != null) {
            restaurantId = idHolder.getRestaurantId();
        }
        if (!auth.isAdmin()) {
            addNewDishButton.setVisibility(View.GONE);
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        firestoreMain.getRestaurantMenu(adapter, restaurantId);



        //Filterbuttons....

       category1Button.setOnClickListener(view -> {
           adapter.getFilter().filter(firestoreMain.getCategory1());

       });

       category2Button.setOnClickListener(view -> {
           adapter.getFilter().filter(firestoreMain.getCategory2());

       });

       category3Button.setOnClickListener(view -> {
           adapter.getFilter().filter(firestoreMain.getCategory3());

       });

       category4Button.setOnClickListener(view -> {
           adapter.getFilter().filter(firestoreMain.getCategory4());

       });

      category5Button.setOnClickListener(view -> {
           adapter.getFilter().filter(firestoreMain.getCategory5());

       });

       wholeMenuButton.setOnClickListener(view -> {
          adapter.getFilter().filter("");
       });

       addNewDishButton.setOnClickListener(view -> {
           eventHandler.openAddishFragment(view);

        });
        }




    @Override
    public void onStop(){
        super.onStop();
        //Deletes local list
        adapter.clearList();
        firestoreMain.detachSnapShotListener();
    }



}






