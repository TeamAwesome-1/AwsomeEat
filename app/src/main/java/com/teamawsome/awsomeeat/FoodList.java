package com.teamawsome.awsomeeat;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.teamawsome.awsomeeat.Common.Common;
import com.teamawsome.awsomeeat.Interface.ItemClickListener;
import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;

    String categoryId="";
    private static final String TAG = "Logging Example";


    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //Firebase_init
        database=FirebaseDatabase.getInstance();
        foodList=database.getReference("Foods");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //get intent here
        if(getIntent() !=null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if(!categoryId.isEmpty() && categoryId !=null)
        {
            if(Common.isNetworkAvailable(getBaseContext()))
                loadListFood(categoryId);
            else
            {
                Toast.makeText(FoodList.this, "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }

    private void loadListFood(String categoryId) {

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("MenuId").equalTo(categoryId)  //similar to select * from food where MenuId=
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                Log.d(TAG,"picaasso" );

                viewHolder.food_name.setText(model.getName());
                Picasso.get().load(model.getImage())
                        .into(viewHolder.food_image);



                final Food local =  model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //start new activity
                        Intent foodDetail = new Intent(FoodList.this, FoodDetail.class);
                        foodDetail.putExtra("FoodId", adapter.getRef(position).getKey()); //send id to new activity
                        startActivity(foodDetail);
                    }
                });

            }
        };
        //set adapter
        recyclerView.setAdapter(adapter);
    }
}