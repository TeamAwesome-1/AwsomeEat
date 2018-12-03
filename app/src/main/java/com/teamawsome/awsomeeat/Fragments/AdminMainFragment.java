package com.teamawsome.awsomeeat.Fragments;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.teamawsome.awsomeeat.Admin.FragmentAddDish;

import com.teamawsome.awsomeeat.Admin.ViewPagerAdapter;
import com.teamawsome.awsomeeat.EventHandler;
import com.teamawsome.awsomeeat.R;


public class AdminMainFragment extends Fragment {


    private TabLayout tableLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    public AdminMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_activity_new, container, false );
        Button addNewRest = view.findViewById(R.id.addNewRestButton);
        Button editRest = view.findViewById(R.id.editRestButton);
        addNewRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventHandler.openAddRestaurantFragment(view);
            }
        });
        editRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventHandler.openRestaurantListFragment(v);
            }
        });

        tableLayout= (TabLayout) view.findViewById(R.id.tablayout_id);
        appBarLayout=(AppBarLayout) view.findViewById(R.id.appbarid);
        viewPager=(ViewPager) view.findViewById(R.id.viewPager);

       /* ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        adapter.AddFragment(new FragmentAddDish(),"Add Dish");
        adapter.AddFragment(new FragmentAddResturant(),"Add Resturant");*/

//        viewPager.setAdapter(adapter);
        //tableLayout.setupWithViewPager(viewPager);

        return view;
    }




}
