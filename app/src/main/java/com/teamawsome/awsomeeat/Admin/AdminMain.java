package com.teamawsome.awsomeeat.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.teamawsome.awsomeeat.Firestore;
import com.teamawsome.awsomeeat.R;

public class AdminMain extends AppCompatActivity {
    private FirestoreMain firestoreMain;
    private FirebaseFirestore firebaseFirestore;
    public EditText restaurantName;
    public EditText restaurantAdress;
    public String restaurantName1;
    public String restaurantAdress1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        firestoreMain = FirestoreMain.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

       restaurantName = findViewById(R.id.restaurantName);
       restaurantAdress = findViewById(R.id.restaurantAdress);



        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantName1 = restaurantName.getText().toString();
                restaurantAdress1 = restaurantAdress.getText().toString();
                restaurantAdress.setText(restaurantAdress1);
                restaurantName.setText(restaurantName1);
              firestoreMain.addRestaurant(restaurantName1, restaurantAdress1);
            }
        });
    }
}
