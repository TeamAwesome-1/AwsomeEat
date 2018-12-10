package com.teamawsome.awsomeeat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipData;
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
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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
    private EventHandler eventHandler = EventHandler.getInstance();
    TextView textCartItemCount;
    int mCartItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        authentication.loadAuthData();
        authentication.getUserDetails();
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

                navigationView.getMenu().findItem(i);

                if (authentication.isAdmin()) {
                    Menu admin = navigationView.getMenu();
                    admin.findItem(R.id.adminItem).setVisible(true);



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

            fragment = new RestaurantListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragmentinsertlayout, fragment);
            fragmentTransaction.commit();

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
        alertdialog.setTitle(R.string.sign_out);
        alertdialog.setMessage(R.string.do_you_really_want_to_close_the_app);
        alertdialog.setIcon(R.drawable.ic_exit_to_app_black_24dp);

        //Inserts a Yes-button with a clicklistener
        alertdialog.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                signOut();
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

        final MenuItem menuItem = menu.findItem(R.id.cart);


        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cart) {
         eventHandler.openCartFragment(getCurrentFocus());
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
           eventHandler.openRestaurantListFragment(getCurrentFocus());
        } else if (id == R.id.nav_cart) {
            //Handle what happens when "cart" is pressed in navigationbar
          eventHandler.openCartFragment(getCurrentFocus());
        } else if (id == R.id.edit_profile){
          eventHandler.openEditProfileFragment(getCurrentFocus());
        }
        else if (id == R.id.nav_log_out) {
            signOut();
        }


        else if (id == R.id.adminItem) {
            eventHandler.openAdminFragment(getCurrentFocus());
        }

        //Closes the drawer after an item has been selected
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void getCartNumber(){

        mCartItemCount = firestoreMain.getCounterIcon();
    }

    @Override
    protected void onResume() {
        super.onResume();
       authentication.checkAuthState(context);
    }

    private void signOut() {
        authentication.logOut();
        Intent signIn= new Intent(AwsomeEatActivity.this, MainActivity.class);
        signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(signIn);
    }

}

