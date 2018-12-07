package com.teamawsome.awsomeeat.Admin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.teamawsome.awsomeeat.Adapters.FoodListRecyclerViewAdapter;
import com.teamawsome.awsomeeat.EventHandler;
import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.PictureHandler;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.idHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditRestaurantMenuFragment extends Fragment implements View.OnClickListener  {
    private FirestoreMain firestoreMain = FirestoreMain.getInstance();
    View view;
    CollectionReference foods;
    public static Food food;
    private EditText dishNameEdit;
   private EditText dishPriceEdit;
    private Button updateDish;
    private FloatingActionButton newDish;
    private FoodListRecyclerViewAdapter adapter;
    private LayoutInflater inflater;
    private String category;
    private EditText picUrl;
    private String picUrlString;
    private ImageView picDish;


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.addNewDishButton:
    addNewDish(v);
    break;
            case R.id.updateDishButton:
    setUpdateDish1(v);
    break;

        }
    }


    public EditRestaurantMenuFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_edit_restaurant_menu,container,false);

        picDish = view.findViewById(R.id.picdishedit_imageview);
        picUrl = view.findViewById(R.id.picurledit_edittext);





        Spinner spinner = (Spinner) view.findViewById(R.id.spinnerAddDish1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.categories));
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
                        category = adapter.getItem(0).toString();
                        break;
                    case 2:
                        category = adapter.getItem(2).toString();
                        break;
                    case 3:
                        category = adapter.getItem(3).toString();
                        break;
                    case 4:
                        category = adapter.getItem(4).toString();
                        break;
                    case 5:
                        category = adapter.getItem(5).toString();
                        break;
                }
            }



        });


        dishNameEdit = view.findViewById(R.id.food_name);
        dishPriceEdit = view.findViewById(R.id.food_price_edittext);

       // dishNameEdit.setText(food.getName());
       // dishPriceEdit.setText(food.getPrice());
        updateDish = view.findViewById(R.id.updateDishButton);

   //TODO: Göra det möjligt att uppdatera food objekt ifrån Collection "Foods".
     //TODO:   använd food objektet.
    //    public void imageClicked () {
      //  dishPic.setImageResource();
       // }


        return view;
        }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



       // ImageView dishPic = view.findViewById(R.id.picDish);
        food = idHolder.getSeletedFood();

        PictureHandler.setPictureFromUrl(food.getImage(), picDish);
        dishNameEdit.setText(food.getName());
        dishPriceEdit.setText(food.getPrice());
      //  PictureHandler.setPictureFromUrl(food.getImage(), dishPic);
        newDish = view.findViewById(R.id.addNewDishButton);

       updateDish.setOnClickListener(this::setUpdateDish1);
        }









        public void setUpdateDish1 (View v){

            picUrlString = picUrl.getText().toString();
            PictureHandler.setPictureFromUrl(picUrlString, picDish);

 dishNameEdit.setText(dishNameEdit.getText());
 dishPriceEdit.setText(dishPriceEdit.getText());

    food.setName(dishNameEdit.getText().toString());
    food.setPrice(dishPriceEdit.getText().toString());
    food.setRestaurantId(idHolder.getRestaurantId());
    food.Category = category;
    food.setImage(picUrlString);
    changeItem();


    }

    public void imageClicked (View view) {

    }

    public void changeItem () {
        adapter = new FoodListRecyclerViewAdapter ();

        adapter.modifyItem(food.getId(), food);
        adapter.notifyDataSetChanged();
        firestoreMain.changeFood(food);
    }

    public void addNewDish (View v) {
       EventHandler.openAddishFragment(v);


            }

    }








