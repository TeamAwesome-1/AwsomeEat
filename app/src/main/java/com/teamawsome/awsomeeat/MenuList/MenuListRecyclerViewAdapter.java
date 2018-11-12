package com.teamawsome.awsomeeat.MenuList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.ViewHolder.FoodViewHolder;

import java.util.List;

public class MenuListRecyclerViewAdapter extends RecyclerView.Adapter<FoodViewHolder> {
    List<Food> list;


    public MenuListRecyclerViewAdapter(List<Food> list){
        this.list = list;
    }
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.food_item, viewGroup, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder foodViewHolder, int position) {
        Food menuItem = list.get(position);
        foodViewHolder.setData(menuItem);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(Food listItem){
        list.add(listItem);
        this.notifyItemInserted(list.size() - 1);
    }
}
