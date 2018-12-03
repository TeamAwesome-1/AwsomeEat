package com.teamawsome.awsomeeat.Admin;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.teamawsome.awsomeeat.R;


public class AdminMainActivity extends AppCompatActivity {

    private TabLayout tableLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_new);
        tableLayout= (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout=(AppBarLayout) findViewById(R.id.appbarid);
        viewPager=(ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new FragmentAddDish(),"Add Dish");
        adapter.AddFragment(new FragmentAddRestaurant(),"Add Resturant");

        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);

    }
}



