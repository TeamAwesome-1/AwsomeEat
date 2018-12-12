package com.teamawsome.awsomeeat.ViewHolder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamawsome.IdHolder;
import com.teamawsome.awsomeeat.Database.Authentication;


import com.teamawsome.awsomeeat.EventHandler;
import com.teamawsome.awsomeeat.PictureHandler;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.Model.Restaurant;
import com.teamawsome.IdHolder;

public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final View restaurantListView;
    private final TextView restaurantListNameView;
    private final TextView restaurantListAdressView;
    private final ImageView restaurantListImageView;
    private String itemId;
    private Restaurant restaurant;
    private Authentication auth = Authentication.getInstance();
    private EventHandler eventHandler = EventHandler.getInstance();
    private PictureHandler pictureHandler = PictureHandler.getInstance();
    private IdHolder idHolder = IdHolder.getInstance();



    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);
        restaurantListView = itemView;
        restaurantListNameView = itemView.findViewById(R.id.name);
        restaurantListAdressView = itemView.findViewById(R.id.adress);
        restaurantListImageView = itemView.findViewById(R.id.restaurant_image);

        restaurantListView.setOnClickListener(this);

    }

    public void setData(String title, String subtitle) {
        restaurantListNameView.setText(title);
        restaurantListAdressView.setText(subtitle);
    }

    //Set information for the listview
    public void setData(Restaurant restaurantInfo) {
        restaurantListNameView.setText(restaurantInfo.name);
        restaurantListAdressView.setText(restaurantInfo.adress);
        itemId = restaurantInfo.getId();
        pictureHandler.setPictureFromUrl(restaurantInfo.pictureUrl, restaurantListImageView);
        restaurant = restaurantInfo;
    }

    @Override
    public void onClick(View v) {
        //Save id for the restaurant that was clicked on
        idHolder.setRestaurantId(itemId);
        idHolder.setRestaurant(restaurant);
        eventHandler.openFoodListFragment(v);
    }
}
