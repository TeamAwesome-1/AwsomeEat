package com.teamawsome.awsomeeat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
    private static final String BASE_FRAGMENT_TAG = "baseFragment";


    private static void openFragment(Fragment fragment, View v, String baseFragmentTag){

        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentinsertlayout, fragment).addToBackStack(baseFragmentTag)
                .commit();
    }


    private static void openFragment(Fragment fragment, View v){
        AppCompatActivity activity = (AppCompatActivity) v.getContext();

        if(activity.getSupportFragmentManager().findFragmentByTag(BASE_FRAGMENT_TAG) == null){
            openFragment(fragment, v, BASE_FRAGMENT_TAG);
        }else {
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentinsertlayout, fragment).addToBackStack(null)
                    .commit();
        }
    }

    /**
     * opens restaurantFragment that is also basefragment in app.
     * @param v
     */
    public static void openRestaurantListFragment(View v){
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        FragmentManager fm = activity.getSupportFragmentManager();
        fm.popBackStack(BASE_FRAGMENT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static void openAddRestaurantFragment(View v){
        fragment = new FragmentAddRestaurant();
        openFragment(fragment, v);
    }

    public static void openCartFragment(View v){
        fragment = new CartFragment();
        openFragment(fragment, v);
    }

    public static void openFoodListFragment(View v){
        fragment = new FoodListFragment();
        openFragment(fragment, v);
    }

    public static void openFoodDetailFragment(View v){
        fragment = new FoodDetailFragment();
        openFragment(fragment, v);
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
