package com.teamawsome.awsomeeat.Adapters;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.teamawsome.awsomeeat.Model.Category;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.ViewHolder.FoodListViewHolder;
import java.util.List;

public class CategoryListRecyclerViewAdapter extends RecyclerView.Adapter<FoodListViewHolder> {

    List<Category> categoryList;


    public CategoryListRecyclerViewAdapter (List<Category> categoryList){
        this.categoryList = categoryList;
    }


    @NonNull
    @Override
    public FoodListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.menu_item, viewGroup, false);
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

    @Override
    public int getItemCount() {
       return categoryList.size();
    }
}
