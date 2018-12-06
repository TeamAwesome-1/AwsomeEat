package com.teamawsome.awsomeeat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.teamawsome.awsomeeat.Admin.FirestoreMain;
import com.teamawsome.awsomeeat.Database.Authentication;
import com.teamawsome.awsomeeat.Fragments.CartFragment;
import com.teamawsome.awsomeeat.Fragments.RestaurantListFragment;
import com.teamawsome.awsomeeat.Fragments.userFragment;
import com.teamawsome.idHolder;

public class SignUpProfileFields extends AppCompatActivity{

    private static final String TAG = "User";
    private String extraInformation;
    private static FirestoreMain firestoreMain = FirestoreMain.getInstance();
    private static Authentication authentication = Authentication.getInstance();
    private final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Fragment fragment = new userFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentinsertlayout, fragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}

