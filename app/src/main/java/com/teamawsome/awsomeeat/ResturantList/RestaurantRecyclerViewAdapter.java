package com.teamawsome.awsomeeat.ResturantList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.teamawsome.awsomeeat.MenuList.MenuListFragment;
import com.teamawsome.awsomeeat.R;
import java.util.List;


public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {
    List<Restaurant> list;
    Context context;

    public RestaurantRecyclerViewAdapter(Context context, List<Restaurant> list) {
        this.context = context;
        this.list = list;
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
        return list.size();
    }


    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder restaurantViewHolder, int position) {
        Restaurant restaurant = list.get(position);
        restaurantViewHolder.setData(restaurant);

        restaurantViewHolder.RestaurantListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment MenuList = new MenuListFragment();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, MenuList).addToBackStack(null)
                        .commit();

                /*Intent test = new Intent (context, MainActivity.class);
                context.startActivity(test);

                Intent foodlist = new Intent(context, FoodList.class);
                foodlist.putExtra("CategoryId", "11");
                context.startActivity(foodlist);*/

            }
        });
    }

    public void addItem(Restaurant listItem) {
        list.add(listItem);
        this.notifyItemInserted(list.size() - 1);
    }

    public void addItem(Restaurant restaurantItem, int position) {
        if (position >= 0 && position < list.size()) {
            list.add(position, restaurantItem);
            this.notifyItemInserted(position);

        } else {
            addItem(restaurantItem);
        }
    }

    public void removeListItem(int position) {
        if (position > 0 && position < list.size()) {
            list.remove(position);
            this.notifyItemRemoved(position);
        }
    }

        public void removeResturant(String id){
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(id)){
                    removeListItem(i);
                    return;
                }

            }
        }

    }

