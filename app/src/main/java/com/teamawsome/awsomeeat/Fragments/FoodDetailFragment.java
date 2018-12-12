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
import com.teamawsome.awsomeeat.Helpers.IdHolder;
import com.teamawsome.awsomeeat.Database.FirestoreMain;
import com.teamawsome.awsomeeat.Database.Authentication;
import com.teamawsome.awsomeeat.Helpers.EventHandler;
import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.Model.Order;
import com.teamawsome.awsomeeat.Helpers.PictureHandler;
import com.teamawsome.awsomeeat.R;

import javax.annotation.Nullable;

public class FoodDetailFragment extends Fragment {
    private TextView food_name, food_price, food_description, price_annotation;
    private ImageView food_image;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton btnCart;
    private Button continueShoppingButton;
    private Button goToCartButton;
    private ElegantNumberButton numberButton;
    private String foodId;
    private String restaurantId;
    private Food currentFood;
    private static FirestoreMain firestoreMain = FirestoreMain.getInstance();
    private static Authentication authentication = Authentication.getInstance();
    private EventHandler eventHandler = EventHandler.getInstance();
    private PictureHandler pictureHandler = PictureHandler.getInstance();
    private IdHolder idHolder = IdHolder.getInstance();

    public FoodDetailFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_detail, container, false);

        //Init view
        numberButton = view.findViewById(R.id.number_button);
        btnCart = view.findViewById(R.id.btnCart);
        price_annotation = view.findViewById(R.id.price_annotation);
        food_description = view.findViewById(R.id.food_description);
        food_name = view.findViewById(R.id.food_name);
        food_price = view.findViewById(R.id.food_price);
        food_image = view.findViewById(R.id.image_food);
        continueShoppingButton = view.findViewById(R.id.Continue_shopping_button);
        goToCartButton = view.findViewById(R.id.go_to_cart_button);
        collapsingToolbarLayout = view.findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //set id for the fooditem selected from foodlist
        foodId = idHolder.getFoodId();
        restaurantId = idHolder.getRestaurantId();
        //get the Food-item selected in the foodlist.
        currentFood = idHolder.getSelectedFood();
        displayInformationAboutFood(currentFood);


        //Specifies what happens when cartbutton is clicked
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = numberButton.getNumber();
                Order order = new Order(restaurantId,foodId,currentFood.getName(),amount,currentFood.getPrice(), authentication.getCurrentUser().getUid());

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
                    eventHandler.openRestaurantListFragment(view);
                }
            }
        });

        //Specifies what happens when Go to cart- button is clicked
        goToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventHandler.openCartFragment(view);
            }
        });


    }

    private void displayInformationAboutFood(Food currentFood) {
        //set image
        pictureHandler.setPictureFromUrl(currentFood.getImage(), food_image);
        //Set the rest of the information
        collapsingToolbarLayout.setTitle(currentFood.getName());
        food_name.setText(currentFood.getName());
        food_description.setText(currentFood.getDescription());

        //Checks if a price is set for the fooditem and if it only contains numbers
       if(currentFood.getPrice()!= null && currentFood.getPrice().matches("[0-9]+")) {
            food_price.setText(currentFood.getPrice());
        }else{
            currentFood.setPrice("0");
            food_price.setText(currentFood.getPrice());
            price_annotation.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        authentication.checkAuthState(getActivity());
    }

}
