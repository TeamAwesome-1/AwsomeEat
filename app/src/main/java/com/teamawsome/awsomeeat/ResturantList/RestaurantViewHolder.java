package com.teamawsome.awsomeeat.ResturantList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.teamawsome.awsomeeat.R;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {
    public final View RestaurantListView;
    public final TextView restaurantListNameView;
    public final TextView restaurantListAdressView;
    public final ImageView restaurantListImageView;


    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);
        this.RestaurantListView = itemView;
        restaurantListNameView = (TextView) itemView.findViewById(R.id.name);
        restaurantListAdressView = (TextView) itemView.findViewById(R.id.adress);
        restaurantListImageView = (ImageView) itemView.findViewById(R.id.restaurant_image);
    }

    public void setData(String title, String subtitle) {
        restaurantListNameView.setText(title);
        restaurantListAdressView.setText(subtitle);
    }

    public void setData(Restaurant restaurantInfo) {
        restaurantListNameView.setText(restaurantInfo.name);
        restaurantListAdressView.setText(restaurantInfo.adress);
        Picasso.get().load(restaurantInfo.pictureUrl).into(restaurantListImageView);
    }
}
