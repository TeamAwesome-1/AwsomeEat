package com.teamawsome.awsomeeat.Fragments;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.teamawsome.awsomeeat.Common.Common;
import com.teamawsome.awsomeeat.Database.Database;
import com.teamawsome.awsomeeat.EventHandler;
import com.teamawsome.awsomeeat.FoodDetail;
import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.Model.Order;
import com.teamawsome.awsomeeat.R;

import javax.annotation.Nullable;

public class FoodDetailFragment extends Fragment {


    TextView food_name, food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String foodId="";

    FirebaseDatabase database;
    DatabaseReference foods;

    Food currentFood;
    public FoodDetailFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_detail, container, false);

        //firebase

        database= FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");

        //Init view
        numberButton = (ElegantNumberButton)view.findViewById(R.id.number_button);
        btnCart = (FloatingActionButton)view.findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getContext()).addToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice()
                ));

                Toast.makeText(getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
                EventHandler.openCartFragment(view, "hej");
            }
        });

        food_description = view.findViewById(R.id.food_description);
        food_name = view.findViewById(R.id.food_name);
        food_price = (TextView)view.findViewById(R.id.food_price);
        food_image = (ImageView)view.findViewById(R.id.image_food);

        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);



        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        foodId=getArguments().get("CategoryId").toString();
        if(!foodId.isEmpty())
        {
            if(Common.isNetworkAvailable(getContext()))
                getDetailFood(foodId);
            else
            {
                Toast.makeText(getContext(), "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentFood =dataSnapshot.getValue(Food.class);
                //TODO change into method
                //set image
                Picasso.get().load(currentFood.getImage())
                        .into(food_image);

                collapsingToolbarLayout.setTitle(currentFood.getName());

                food_price.setText(currentFood.getPrice());

                food_name.setText(currentFood.getName());

                food_description.setText(currentFood.getDescription());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
