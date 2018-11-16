package com.teamawsome.awsomeeat;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.teamawsome.awsomeeat.Fragments.RestaurantListFragment;

public class AwsomeEatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awsome_eat);

        RestaurantListFragment restaurantListFragment = new RestaurantListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .add(R.id.linearLayout1, restaurantListFragment);
        fragmentTransaction.commit();

    }

    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }
        else{
            finish();
        }
    }

}
