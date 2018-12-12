package com.teamawsome.awsomeeat;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.teamawsome.awsomeeat.Admin.FirestoreMain;
import com.teamawsome.awsomeeat.Database.Authentication;
import com.teamawsome.awsomeeat.Fragments.userFragment;


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
        Bundle bundle = new Bundle();
        bundle.putString("Info_key","Info");
        Fragment fragment = new userFragment();
        fragment.setArguments(bundle);
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

