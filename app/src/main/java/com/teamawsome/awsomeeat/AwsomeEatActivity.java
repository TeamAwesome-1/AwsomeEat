package com.teamawsome.awsomeeat;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.teamawsome.awsomeeat.Admin.FirestoreMain;
import com.teamawsome.awsomeeat.Database.Authentication;

public class AwsomeEatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "User";
    private static FirestoreMain firestoreMain = FirestoreMain.getInstance();
    private static Authentication authentication = Authentication.getInstance();
    private EventHandler eventHandler = EventHandler.getInstance();
    TextView textCartItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        });


        // Starts the fragment shown on first page in app
        FragmentManager fragmentManager = getSupportFragmentManager();

            authentication.openFragment(fragmentManager);
}


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
                finishAffinity();
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

        View actionView = menuItem.getActionView();
        textCartItemCount = actionView.findViewById(R.id.cart_badge);

        setupBadge();


        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        firestoreMain.updateCounter(() ->  setupBadge());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cart) {
         eventHandler.openCartFragment(getSupportFragmentManager());
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
            eventHandler.openRestaurantListFragment(getSupportFragmentManager());

        } else if (id == R.id.nav_cart) {
            //Handle what happens when "cart" is pressed in navigationbar
          eventHandler.openCartFragment(getSupportFragmentManager());

        } else if (id == R.id.edit_profile){
          eventHandler.openEditProfileFragment(getSupportFragmentManager());
        }
        else if (id == R.id.nav_log_out) {
            signOut();
        }


        else if (id == R.id.adminItem) {
            eventHandler.openAdminFragment(getSupportFragmentManager());
        }

        //Closes the drawer after an item has been selected
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (firestoreMain.getCounter() == 0) {
                      textCartItemCount.setVisibility(View.GONE);
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(firestoreMain.getCounter(), 99)));
                textCartItemCount.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       authentication.checkAuthState(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        firestoreMain.detachListenerForCounter();
    }

    private void signOut() {
        authentication.logOut();
        Intent signIn= new Intent(AwsomeEatActivity.this, MainActivity.class);
        signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(signIn);
    }
}

