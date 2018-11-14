package com.teamawsome.awsomeeat.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.teamawsome.awsomeeat.Common.PictureHelper;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.ResturantList.Restaurant;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {
    public final View RestaurantListView;
    private final TextView restaurantListNameView;
    private final TextView restaurantListAdressView;
    private final ImageView restaurantListImageView;


    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);
        RestaurantListView = itemView;
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
