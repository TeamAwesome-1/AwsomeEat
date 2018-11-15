package com.teamawsome.awsomeeat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.teamawsome.awsomeeat.Fragments.FoodCategoryFragment;
import com.teamawsome.awsomeeat.Fragments.FoodListFragment;
import com.teamawsome.awsomeeat.Fragments.MenuListFragment;
import com.teamawsome.awsomeeat.Fragments.RestaurantListFragment;

public class EventHandler {

    private static Fragment fragment;

    private static void openFragment(Fragment fragment, View v, String id){
        Bundle bundle = new Bundle();
        bundle.putString("CategoryId", id);
        fragment.setArguments(bundle);
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentinsertlayout, fragment).addToBackStack(null)
                .commit();
    }

    public static void openMenuListFragment(View v, String id){
        fragment = new MenuListFragment();
        openFragment(fragment, v, id);

    }

    public static void openFoodListFragment(View v, String id){
        fragment = new FoodListFragment();
        openFragment(fragment, v, id);

    }


    public static void openRestaurantListFragment(View v, String id){
        fragment = new RestaurantListFragment();
        openFragment(fragment, v, id);
    }

    public static void openFoodCategoryFragment(View v, String id){
        fragment = new FoodCategoryFragment();
        openFragment(fragment, v, id);
    }



}
