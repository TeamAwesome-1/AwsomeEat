package com.teamawsome.awsomeeat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.teamawsome.awsomeeat.Database.Authentication;

public class MainActivity extends AppCompatActivity {

    private Button btnSignIn,btnSignUp;
    private TextView txtSlogan;
    private static Authentication authentication = Authentication.getInstance();
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        authentication.setupFirebaseAuth(this);
        btnSignIn= findViewById(R.id.btnSignIn);
        btnSignUp= findViewById(R.id.btnSignUp);
        txtSlogan = findViewById(R.id.txtSlogan);
        Typeface face=Typeface.createFromAsset(getAssets(),"EC.ttf");
        txtSlogan.setTypeface(face);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(MainActivity.this,SignUp.class);
                startActivity(signup);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signin = new Intent(MainActivity.this,SignIn.class);
                startActivity(signin);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);

            }
        });
        }


    @Override
    protected void onStart() {
        super.onStart();
        authentication.addStateListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        authentication.removeStateListener();
    }
}
