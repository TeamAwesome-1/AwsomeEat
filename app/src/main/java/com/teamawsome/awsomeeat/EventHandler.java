package com.teamawsome.awsomeeat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.teamawsome.awsomeeat.Admin.EditRestaurantMenuFragment;
import com.teamawsome.awsomeeat.Admin.FragmentAddDish;
import com.teamawsome.awsomeeat.Admin.FragmentAddRestaurant;

import com.teamawsome.awsomeeat.Fragments.AdminMainFragment;
import com.teamawsome.awsomeeat.Fragments.CartFragment;
import com.teamawsome.awsomeeat.Fragments.EditProfleFragment;
import com.teamawsome.awsomeeat.Fragments.FoodCategoryFragment;
import com.teamawsome.awsomeeat.Fragments.FoodDetailFragment;
import com.teamawsome.awsomeeat.Fragments.FoodListFragment;
import com.teamawsome.awsomeeat.Fragments.RestaurantListFragment;

public class EventHandler {

    //TODO Check if all methods are neccessary/Sandra
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

    private static void openFragment(Fragment fragment, View v){
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentinsertlayout, fragment).addToBackStack(null)
                .commit();
        }


    public static void openAddRestaurantFragment(View v){
        fragment = new FragmentAddRestaurant();
        openFragment(fragment, v);
    }

    public static void openCartFragment(View v){
        fragment = new CartFragment();
        openFragment(fragment, v);

    }


    public static void openCartFragment(View v, String id){
        fragment = new CartFragment();
        openFragment(fragment, v, id);

    }

    public static void openFoodListFragment(View v){
        fragment = new FoodListFragment();
        openFragment(fragment, v);

    }

    public static void openFoodListFragment(View v, String id){
        fragment = new FoodListFragment();
        openFragment(fragment, v, id);

    }

    public static void openRestaurantListFragment(View v){
        fragment = new RestaurantListFragment();
        openFragment(fragment, v);
    }


    public static void openRestaurantListFragment(View v, String id){
        fragment = new RestaurantListFragment();
        openFragment(fragment, v, id);
    }

    public static void openFoodCategoryFragment(View v, String id){
        fragment = new FoodCategoryFragment();
        openFragment(fragment, v, id);
    }

    public static void openFoodCategoryFragment(View v){
        fragment = new FoodCategoryFragment();
        openFragment(fragment, v);
    }

    public static void openFoodDetailFragment(View v){
        fragment = new FoodDetailFragment();
        openFragment(fragment, v);
    }

    public static void openFoodDetailFragment(View v, String id){
        fragment = new FoodDetailFragment();
        openFragment(fragment, v, id);
    }

    public static void openEditRestaurantMenuFragment(View view) {
        fragment = new EditRestaurantMenuFragment();
        openFragment(fragment, view);
    }

    public static void openAddishFragment (View v) {
        fragment = new FragmentAddDish();
        openFragment(fragment, v);
    }

    public static void openEditProfileFragment(View currentFocus) {
        fragment = new EditProfleFragment();
        openFragment(fragment, currentFocus);
    }

    public static void openAdminFragment(View currentFocus) {
        fragment = new AdminMainFragment();
        openFragment(fragment, currentFocus);
    }
}
