package com.teamawsome.awsomeeat.Fragments;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.teamawsome.awsomeeat.Admin.FirestoreMain;
import com.teamawsome.awsomeeat.Common.Common;
import com.teamawsome.awsomeeat.Database.Database;
import com.teamawsome.awsomeeat.EventHandler;
import com.teamawsome.awsomeeat.FoodDetail;
import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.Model.Order;
import com.teamawsome.awsomeeat.PictureHandler;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.idHolder;

import org.w3c.dom.Text;

import javax.annotation.Nullable;

public class FoodDetailFragment extends Fragment {


    TextView food_name, food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    Button continueShoppingButton;
    Button goToCartButton;
    ElegantNumberButton numberButton;
    String foodId;
    Food currentFood;
    FirestoreMain firestoreMain = FirestoreMain.getInstance();

    public FoodDetailFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_detail, container, false);

        //Init view
        numberButton = (ElegantNumberButton) view.findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) view.findViewById(R.id.btnCart);
        food_description = (TextView) view.findViewById(R.id.food_description);
        food_name = (TextView) view.findViewById(R.id.food_name);
        food_price = (TextView) view.findViewById(R.id.food_price);
        food_image = (ImageView) view.findViewById(R.id.image_food);
        continueShoppingButton = view.findViewById(R.id.Continue_shopping_button);
        goToCartButton = view.findViewById(R.id.go_to_cart_button);
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //set id for the fooditem selected from foodlist
        foodId = idHolder.getFoodId();

        //get the Food-item selected in the foodlist.
        currentFood = idHolder.getSeletedFood();
        displayInformationAboutFood(currentFood);


        //Specifies what happens when cartbutton is clicked
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = numberButton.getNumber();
                Order order = new Order(foodId,currentFood.getName(),amount,currentFood.getPrice(), idHolder.getUserId());

                firestoreMain.addToCart(order);
                Toast.makeText(getContext(), getString(R.string.added_to_cart_toast), Toast.LENGTH_SHORT).show();

            }
        });

        //Specifies what happens when Continue-Shopping-button is clicked
        continueShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0){
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                else{
                    EventHandler.openRestaurantListFragment(view);
                }
            }
        });

        //Specifies what happens when Go to cart- button is clicked
        goToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EventHandler.openCartFragment(view);
            }
        });


    }

    private void displayInformationAboutFood(Food currentFood) {
        //set image
        PictureHandler.setPictureFromUrl(currentFood.getImage(), food_image);
        //Set the rest of the information
        collapsingToolbarLayout.setTitle(currentFood.getName());
        food_price.setText(currentFood.getPrice());
        food_name.setText(currentFood.getName());
        food_description.setText(currentFood.getDescription());

    }

}
