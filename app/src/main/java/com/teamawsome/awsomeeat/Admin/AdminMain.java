package com.teamawsome.awsomeeat.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.teamawsome.awsomeeat.AwsomeEatActivity;
import com.teamawsome.awsomeeat.R;

public class AdminMain extends AppCompatActivity {
    private FirestoreMain firestoreMain;
    private FirebaseFirestore firebaseFirestore;
    public EditText restaurantName;
    public EditText restaurantAdress;
    public String restaurantName1;
    public String restaurantAdress1;
    public EditText addDish;
    public String addDish1;
    public RadioButton europeanButton, chineseButton, pizzaButton;
    public String category;
    public RadioGroup foodCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        firestoreMain = FirestoreMain.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        addDish = findViewById(R.id.dishAddEdit);
        restaurantName = findViewById(R.id.restaurantName);
        restaurantAdress = findViewById(R.id.restaurantAdress);
        Button submitFood = findViewById(R.id.submitFoodButton);
        Button submitRestaurant = findViewById(R.id.submit);
        Button admin = findViewById(R.id.adminItem);
        foodCategory = findViewById(R.id.foodCategory);
        submitRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantName1 = restaurantName.getText().toString();
                restaurantAdress1 = restaurantAdress.getText().toString();
                restaurantAdress.setText(restaurantAdress1);
                restaurantName.setText(restaurantName1);
              //  firestoreMain.addRestaurant(restaurantName1, restaurantAdress1);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplication(), AdminMainActivity.class));
            }
        });


        foodCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.swedishButton) {
                    category = "Swedish";
                }
                else if (checkedId == R.id.chineseButton) {
                    category = "Chinese";
                }
                else if (checkedId == R.id.pizzaButton) {
                    category = "Pizza";
                }

            }


        });

        submitFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDish1 = addDish.getText().toString();
                addDish.setText(addDish1);
              //  firestoreMain.addFood(addDish1, category, restaurantId);
            }
        });

    }

}

