package com.teamawsome.awsomeeat.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.Model.Restaurant;
import com.teamawsome.awsomeeat.ViewHolder.RestaurantViewHolder;
import java.util.List;


public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {
    private List<Restaurant> restaurantsList;

    public RestaurantRecyclerViewAdapter( List<Restaurant> list) {

        this.restaurantsList = list;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.restaurant_item, viewGroup, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return restaurantsList.size();
    }


    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder restaurantViewHolder, int position) {
        Restaurant restaurant = restaurantsList.get(position);
        restaurantViewHolder.setData(restaurant);
    }

    public void addItem(Restaurant listItem) {
        restaurantsList.add(listItem);
        this.notifyItemInserted(restaurantsList.size() - 1);
    }

    public void addItem(Restaurant restaurantItem, int position) {
        if (position >= 0 && position < restaurantsList.size()) {
            restaurantsList.add(position, restaurantItem);
            this.notifyItemInserted(position);

        } else {
            addItem(restaurantItem);
        }
    }

    private void removeListItem(int position) {
        if (position >= 0 && position < restaurantsList.size()) {
            restaurantsList.remove(position);
            this.notifyItemRemoved(position);
        }
    }

    public void removeResturant(String id){
            for (int i = 0; i < restaurantsList.size(); i++) {
                if (restaurantsList.get(i).getId().equals(id)){
                    removeListItem(i);
                    return;
                }

            }
        }

    public void modifyRestaurant(String id, Restaurant restaurant) {

        for (int i = 0; i < restaurantsList.size(); i++) {
            if (restaurantsList.get(i).getId().equals(id)) {
                restaurantsList.set(i, restaurant);
                this.notifyItemChanged(i);
                return;
            }
        }
    }
    public void clearList(){
        restaurantsList.clear();
        this.notifyItemRangeRemoved(0,restaurantsList.size());
    }

}

