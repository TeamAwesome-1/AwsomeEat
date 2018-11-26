package com.teamawsome.awsomeeat.ViewHolder;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamawsome.awsomeeat.EventHandler;
import com.teamawsome.awsomeeat.Model.Category;
import com.teamawsome.awsomeeat.PictureHandler;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.idHolder;

public class FoodListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public View menuItemView;
    public TextView txtMenuName;
    public ImageView imageView;
    public String itemId;
    private  String restaurantId;

    //private ItemClickListener itemClickListener;

    public FoodListViewHolder(View itemView) {
        super(itemView);

        menuItemView = itemView;
        txtMenuName = (TextView) itemView.findViewById(R.id.menu_name);
        imageView = (ImageView) itemView.findViewById(R.id.menu_image);

        menuItemView.setOnClickListener(this);
    }

    /*public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }*/

    public void setData(Category foodCategoryInfo){
        txtMenuName.setText(foodCategoryInfo.getName());
        PictureHandler.setPictureFromUrl(foodCategoryInfo.getImage(), imageView);
        itemId=foodCategoryInfo.getId();
    }

    @Override
    public void onClick(View view) {
        idHolder.setCategoryId(itemId);
        EventHandler.openFoodListFragment(view);
    }
}
