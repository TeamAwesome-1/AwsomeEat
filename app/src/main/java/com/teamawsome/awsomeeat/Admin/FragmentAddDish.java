package com.teamawsome.awsomeeat.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.PictureHandler;
import com.teamawsome.awsomeeat.R;

public class FragmentAddDish extends Fragment {
    private FirestoreMain firestoreMain = FirestoreMain.getInstance();
    View view;
    EditText setFoodName;
    Button addDish;
    TextView dishPrice;
    Food food;
    RadioGroup foodCategory;
    String category;
    ImageView picDish;
    EditText picUrl;
    String picUrlString;

    public FragmentAddDish() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_add_dish,container,false);
        picDish = view.findViewById(R.id.picdish_imageview);
        picUrl = view.findViewById(R.id.picture_url);




        Spinner spinner = view.findViewById(R.id.spinner2);
       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.categories));
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/
        ArrayAdapter<String> adapter;
        //Loads categories from database
        if(firestoreMain.getCategories()!=null) {
            adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, firestoreMain.getCategories());
        }else {
            adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.categories));
        }
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spinner.setAdapter(adapter);
            adapter.notifyDataSetChanged();



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = "Not assigned";
            }


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                        case 1:
                            category = adapter.getItem(0);
                            break;
                        case 2:
                            category = adapter.getItem(2);
                            break;
                        case 3:
                            category = adapter.getItem(3);
                            break;
                        case 4:
                            category = adapter.getItem(4);
                            break;
                        case 5:
                            category = adapter.getItem(5);
                            break;
                    }
            }



        });

       // foodCategory = view.findViewById(R.id.foodCategory1);
        setFoodName = view.findViewById(R.id.food_name);
        addDish = view.findViewById(R.id.btnAdd);
        dishPrice = view.findViewById(R.id.food_price_edittext);
        addDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewDish();
            }
        });




        return view;



    }

    public void addNewDish () {
        picUrlString = picUrl.getText().toString();
        if (picUrlString.contains("http")) {
         //   picUrlString = picUrl.getText().toString();
            PictureHandler.setPictureFromUrl(picUrlString, picDish);
            firestoreMain.addFood(setFoodName.getText().toString(), dishPrice.getText().toString(), category, picUrlString);
            Toast.makeText(this.getContext(), getString(R.string.dish_added_to_restaurant_menu), Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().popBackStack();
        } else {
            Toast.makeText(this.getContext(), getString(R.string.please_enter_a_valid_picture_url), Toast.LENGTH_SHORT).show();


        }
    }
}

