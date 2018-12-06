package com.teamawsome.awsomeeat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.teamawsome.awsomeeat.Admin.FirestoreMain;
import com.teamawsome.awsomeeat.Database.Authentication;
import com.teamawsome.awsomeeat.Fragments.AdminMainFragment;
import com.teamawsome.awsomeeat.Fragments.CartFragment;
import com.teamawsome.awsomeeat.Fragments.EditProfleFragment;
import com.teamawsome.awsomeeat.Fragments.RestaurantListFragment;
import com.teamawsome.awsomeeat.Fragments.userFragment;
import com.teamawsome.idHolder;

public class AwsomeEatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "User";
    private String extraInformation;
    private static FirestoreMain firestoreMain = FirestoreMain.getInstance();
    private static Authentication authentication = Authentication.getInstance();
    private final Context context = this;
    private Fragment fragment;

    public String getExtraInformation() {
        return extraInformation;
    }
    private int count=0;
    public void setExtraInformation(String extraInformation) {
        this.extraInformation = extraInformation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        authentication.loadAuthData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }
            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {
                //TODO kolla på detta. Hur gömma adminknappen/Shahin
                navigationView.getMenu().findItem(i);
                if (authentication.isAdmin() && count==0) {
                    count++;
                    navigationView.getMenu().add(0, 10, 7, "Admin");
                    navigationView.getMenu().findItem(10).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            return true;
                        }

                    });

                }
            }


               /* MenuItem admin = findViewById(R.id.admin);
                admin.setVisible(false);
               if (authentication.isAdmin()) {
                   admin.setVisible(true);
                   return;
               }
                 else if (!authentication.isAdmin()){
               }
            }*/
        });


        // Starts the fragment shown on first page in app

        // TODO:
        if (!authentication.isAdmin()){
            fragment = new RestaurantListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragmentinsertlayout, fragment);
            fragmentTransaction.commit();
        }else{
            fragment = new AdminMainFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragmentinsertlayout, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }
        else{
            DisplayExitDialog();

        }
    }

    private void DisplayExitDialog() {

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle(R.string.exit);
        alertdialog.setMessage(R.string.do_you_really_want_to_close_the_app);
        alertdialog.setIcon(R.drawable.ic_exit_to_app_black_24dp);

        //Inserts a Yes-button with a clicklistener
        alertdialog.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        //Inserts a No-button with a clicklistener to the alertdialog
        alertdialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        //displays the alerDialog
        alertdialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_view) {

        } else if (id == R.id.nav_menu) {
            //Handle what happens when "menu" is pressed in navigationbar
            if(idHolder.getRestaurantId()!= null){
                EventHandler.openFoodListFragment(getCurrentFocus());
            }else{
                Toast.makeText(this, getString(R.string.choose_an_restaurant), Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_orders) {

        } else if (id == R.id.nav_cart) {
          EventHandler.openCartFragment(getCurrentFocus());

            fragment = new RestaurantListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentinsertlayout, fragment);
            fragmentTransaction.commit();
        }  else if (id == R.id.nav_cart) {
            //Handle what happens when "cart" is pressed in navigationbar
            fragment = new CartFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentinsertlayout, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.edit_profile){
           EventHandler.openEditProfileFragment(getCurrentFocus());
        }
        else if (id == R.id.nav_log_out) {
            //Logout

            authentication.logOut();
            Intent signIn= new Intent(AwsomeEatActivity.this, MainActivity.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }

        else if (id == R.id.admin) {
            EventHandler.openAdminFragment(getCurrentFocus());
        }

        //Closes the drawer after an item has been selected
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        authentication.checkAuthState(context);
        authentication.loadAuthData();
    }

}

