package com.teamawsome.awsomeeat.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.teamawsome.awsomeeat.Helpers.IdHolder;
import com.teamawsome.awsomeeat.Database.Authentication;
import com.teamawsome.awsomeeat.Helpers.EventHandler;
import com.teamawsome.awsomeeat.Helpers.PictureHandler;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.Model.Restaurant;

public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private View restaurantListView;
    private TextView restaurantListNameView;
    private TextView restaurantListAdressView;
    private ImageView restaurantListImageView;
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
