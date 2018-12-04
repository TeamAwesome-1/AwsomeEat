package com.teamawsome.awsomeeat.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.R;

public class FragmentAddDish extends Fragment {
    private FirestoreMain firestoreMain = FirestoreMain.getInstance();
    View view;
    EditText setFoodName;
    Button addDish;
    TextView dishPrice;
    Food food;
    RadioGroup foodCategory;
    String Category;

    public FragmentAddDish() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_add_dish,container,false);

        String [] values = {"Resturant1","Resturant2","Resturant3","Resturant4"};
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_resturantName);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        foodCategory = view.findViewById(R.id.foodCategory1);
        setFoodName = view.findViewById(R.id.food_name);
        addDish = view.findViewById(R.id.btnAdd);
        dishPrice = view.findViewById(R.id.dish_price);
        addDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewDish();
            }
        });


        foodCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.swedishButton) {
                    Category = "Pizza";
                }
                else if (checkedId == R.id.chineseButton) {
                    Category = "Chinese";
                }
                else if (checkedId == R.id.pizzaButton) {
                    Category = "Pizza";
                }

            }


        });

        return view;



    }

    public void addNewDish () {

        firestoreMain.addFood(setFoodName.getText().toString(), dishPrice.getText().toString(), Category);
    }
}

