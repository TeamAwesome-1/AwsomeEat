package com.teamawsome.awsomeeat.Adapters;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawsome.awsomeeat.Database.Authentication;
import com.teamawsome.awsomeeat.EventHandler;
import com.teamawsome.awsomeeat.Model.Category;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.ViewHolder.FoodListViewHolder;
import java.util.List;

public class CategoryListRecyclerViewAdapter extends RecyclerView.Adapter<FoodListViewHolder> {

    List<Category> categoryList;
    FloatingActionButton addnewDish;
    Authentication authentication = Authentication.getInstance();
    EventHandler eventHandler = EventHandler.getInstance();

    public CategoryListRecyclerViewAdapter (List<Category> categoryList){
        this.categoryList = categoryList;
    }


    @NonNull
    @Override
    public FoodListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.menu_item, viewGroup, false);
        addnewDish = view.findViewById(R.id.addNewDishButton);
       // TODO: Fixa authenction.isAdmin så att den fungerar.
      // if (authentication.isAdmin()) {
            addnewDish.setVisibility(view.VISIBLE);
     //  }
        addnewDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventHandler.openAddishFragment(view);
            }
        });
        return new FoodListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListViewHolder foodListViewHolder, int position) {
            Category category = categoryList.get(position);
            foodListViewHolder.setData(category);
    }

    public void addCategoryItem(Category categoryItem){
        categoryList.add(categoryItem);
        this.notifyItemInserted(categoryList.size()-1);


    }


    public void modifyItem(String id, Category category) {
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getId().equals(id)) {
                categoryList.set(i, category);
                this.notifyItemChanged(i);
                return;
            }
        }
    }


    public void removeMenuItem(int position) {
        if (position > 0 && position < categoryList.size()) {
            categoryList.remove(position);
            this.notifyItemRemoved(position);
        }
    }

    public void removeMenuItem(String id){
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getId().equals(id)){
                removeMenuItem(i);
                return;
            }
        }
    }


    @Override
    public int getItemCount() {
       return categoryList.size();
    }

    public void clearList(){
        categoryList.clear();
        this.notifyItemRangeRemoved(0,getItemCount());
    }
}
