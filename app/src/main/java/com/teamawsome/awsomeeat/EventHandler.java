package com.teamawsome.awsomeeat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.teamawsome.awsomeeat.Admin.EditRestaurantMenuFragment;
import com.teamawsome.awsomeeat.Admin.FragmentAddDish;
import com.teamawsome.awsomeeat.Admin.FragmentAddRestaurant;

import com.teamawsome.awsomeeat.Admin.FragmentEditRestaurant;
import com.teamawsome.awsomeeat.Fragments.AdminMainFragment;
import com.teamawsome.awsomeeat.Fragments.CartFragment;
import com.teamawsome.awsomeeat.Fragments.EditProfleFragment;
import com.teamawsome.awsomeeat.Fragments.FoodCategoryFragment;
import com.teamawsome.awsomeeat.Fragments.FoodDetailFragment;
import com.teamawsome.awsomeeat.Fragments.FoodListFragment;
import com.teamawsome.awsomeeat.Fragments.RestaurantListFragment;

/**
 * A class that handles fragmentransitions and fragmentLifecycle.
 * Use Eventhandler to open fragments.
 */
public class EventHandler {
    
    private Fragment fragment;
    private final String BASE_FRAGMENT_TAG;
    private static final EventHandler EventHandler =  new EventHandler();
    private EventHandler () {
        BASE_FRAGMENT_TAG = "baseFragment";
    }


    public static EventHandler getInstance () {
        return EventHandler;
    }



    private void openFragment(Fragment fragment, View v, String baseFragmentTag){

        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentinsertlayout, fragment).addToBackStack(baseFragmentTag)
                .commit();
    }

    private void openFragment(Fragment fragment, FragmentManager fm, String baseFragmentTag){
        fm.beginTransaction()
                .replace(R.id.fragmentinsertlayout, fragment).addToBackStack(baseFragmentTag)
                .commit();
    }

    private void openFragment(Fragment fragment, FragmentManager fm){
        if(fm.findFragmentByTag(BASE_FRAGMENT_TAG) == null){
            openFragment(fragment, fm, BASE_FRAGMENT_TAG);
        }else {
            fm.beginTransaction()
                    .replace(R.id.fragmentinsertlayout, fragment).addToBackStack(null)
                    .commit();
        }
    }

    private void openFragment(Fragment fragment, View v){
        AppCompatActivity activity = (AppCompatActivity) v.getContext();

        if(activity.getSupportFragmentManager().findFragmentByTag(BASE_FRAGMENT_TAG) == null){
            openFragment(fragment, v, BASE_FRAGMENT_TAG);
        }else {
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentinsertlayout, fragment).addToBackStack(null)
                    .commit();
        }
    }

    private void openBaseFragment(View v){
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        FragmentManager fm = activity.getSupportFragmentManager();
        fm.popBackStack(BASE_FRAGMENT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

    private void openBaseFragment(FragmentManager fm){
        fm.popBackStack(BASE_FRAGMENT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }


    /**
     * Opens the baseFragment-RestaurantListfragment.
     * @param v- view
     */
    public void openRestaurantListFragment(View v){
        openBaseFragment(v);
    }

    public void openAddRestaurantFragment(View v){
        fragment = new FragmentAddRestaurant();
        openFragment(fragment, v);
    }

    public void openCartFragment(View v){
        fragment = new CartFragment();
        openFragment(fragment, v);
    }

    public void openFoodListFragment(View v){
        fragment = new FoodListFragment();
        openFragment(fragment, v);
    }

    public void openFoodDetailFragment(View v){
        fragment = new FoodDetailFragment();
        openFragment(fragment, v);
    }

    public void openEditRestaurantMenuFragment(View view) {
        fragment = new EditRestaurantMenuFragment();
        openFragment(fragment, view);
    }

    public void openAddishFragment (View v) {
        fragment = new FragmentAddDish();
        openFragment(fragment, v);
    }

    public void openEditProfileFragment(View v) {
        fragment = new EditProfleFragment();
        openFragment(fragment, v);
    }

    public void openAdminFragmentView(View v) {
        fragment = new AdminMainFragment();
        openFragment(fragment, v);
    }

    public void openRestaurantListFragment(FragmentManager fm){
        openBaseFragment(fm);
    }

    public void openCartFragment(FragmentManager fm){
        fragment = new CartFragment();
        openFragment(fragment, fm);
    }

    public void openAdminFragment(FragmentManager fm) {
        fragment = new AdminMainFragment();
        openFragment(fragment, fm);
    }

    public void openEditProfileFragment(FragmentManager fm) {
        fragment = new EditProfleFragment();
        openFragment(fragment, fm);
    }

    public void openEditRestaurantFragment(FragmentManager fm) {
        fragment = new FragmentEditRestaurant();
        openFragment(fragment, fm);
    }







    }

