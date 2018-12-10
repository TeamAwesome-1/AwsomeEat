package com.teamawsome.awsomeeat.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.teamawsome.awsomeeat.Database.Authentication;
import com.teamawsome.awsomeeat.EventHandler;
import com.teamawsome.awsomeeat.Interface.ItemClickListener;
import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.PictureHandler;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.idHolder;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Authentication auth = Authentication.getInstance();
    private TextView food_name;
    private ImageView food_image;
    private String itemId;
    private Food food;
    private EventHandler eventHandler = EventHandler.getInstance();

    private ItemClickListener itemClickListener;

   /* public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }*/

    public FoodViewHolder(View itemView) {
        super(itemView);

        food_name = itemView.findViewById(R.id.food_name);
        food_image = itemView.findViewById(R.id.food_image);


        //Set cklicklistener on every listitem.
        itemView.setOnClickListener(this);
    }

    public void setData (Food food){
        food_name.setText(food.getName());
        PictureHandler.setPictureFromUrl(food.getImage(), food_image);
        //save the id for specific food
        itemId = food.getId();
        this.food = food;
    }

    @Override
    public void onClick(View view) {

        idHolder.setFoodId(itemId);
        idHolder.setSeletedFood(food);

      if (auth.isAdmin()) {
        eventHandler.openEditRestaurantMenuFragment(view);
       }else {
          eventHandler.openFoodDetailFragment(view);
      }
    }
}
