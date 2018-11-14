package com.teamawsome.awsomeeat.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.teamawsome.awsomeeat.Fragments.MenuListFragment;
import com.teamawsome.awsomeeat.Interface.ItemClickListener;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.ResturantList.Restaurant;

public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final View restaurantListView;
    private final TextView restaurantListNameView;
    private final TextView restaurantListAdressView;
    private final ImageView restaurantListImageView;
    private ItemClickListener itemClickListener;



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

    public void setData(Restaurant restaurantInfo) {
        restaurantListNameView.setText(restaurantInfo.name);
        restaurantListAdressView.setText(restaurantInfo.adress);
        Picasso.get().load(restaurantInfo.pictureUrl).into(restaurantListImageView);
    }

    @Override
    public void onClick(View v) {
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        Fragment MenuList = new MenuListFragment();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.linearLayout1, MenuList).addToBackStack(null)
                .commit();
    }
}
