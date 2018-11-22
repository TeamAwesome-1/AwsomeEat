package com.teamawsome.awsomeeat.ViewHolder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamawsome.awsomeeat.EventHandler;
import com.teamawsome.awsomeeat.Fragments.FoodCategoryFragment;
import com.teamawsome.awsomeeat.Fragments.MenuListFragment;
import com.teamawsome.awsomeeat.Interface.ItemClickListener;
import com.teamawsome.awsomeeat.PictureHandler;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.Model.Restaurant;
import com.teamawsome.idHolder;

public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final View restaurantListView;
    private final TextView restaurantListNameView;
    private final TextView restaurantListAdressView;
    private final ImageView restaurantListImageView;
    private String itemId;



    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);
        restaurantListView = itemView;
        restaurantListNameView = (TextView) itemView.findViewById(R.id.name);
        restaurantListAdressView = (TextView) itemView.findViewById(R.id.adress);
        restaurantListImageView = (ImageView) itemView.findViewById(R.id.restaurant_image);

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
        itemId = restaurantInfo.id;
        PictureHandler.setPictureFromUrl(restaurantInfo.pictureUrl, restaurantListImageView);
    }

    @Override
    public void onClick(View v) {

        //Save id for the restaurant that was clicked on
        idHolder.setRestaurantId(itemId);
        //open next fragment with foodcategories
        EventHandler.openFoodCategoryFragment(v);

    }
}
