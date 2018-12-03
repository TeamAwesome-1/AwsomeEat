package com.teamawsome.awsomeeat.Admin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
public class EditRestaurantMenuFragment extends Fragment {
    private FirestoreMain firestoreMain = FirestoreMain.getInstance();
    View view;
    CollectionReference foods;
    public static Food food;
    private EditText dishNameEdit;
   private EditText dishPriceEdit;
    private Button updateDish;



    public EditRestaurantMenuFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_edit_restaurant_menu,container,false);


        dishNameEdit = view.findViewById(R.id.dishName);
        dishPriceEdit = view.findViewById(R.id.price);

       // dishNameEdit.setText(food.getName());
       // dishPriceEdit.setText(food.getPrice());
        Button updateDish = view.findViewById(R.id.updateDishButton);

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
        getActivity().findViewById(R.id.updateDishButton).setOnClickListener(this::setUpdateDish1);

        ImageView dishPic = view.findViewById(R.id.picDish);
        food = idHolder.getSeletedFood();
        dishNameEdit.setText(food.getName());
        dishPriceEdit.setText(food.getPrice());
        PictureHandler.setPictureFromUrl(food.getImage(), dishPic);
    }






        public void setUpdateDish1 (View v){

dishNameEdit.setText(dishNameEdit.getText());
dishPriceEdit.setText(dishPriceEdit.getText());

    food.setName(dishNameEdit.getText().toString());
    food.setPrice(dishPriceEdit.getText().toString());
    food.setMenuId(food.getId());

    changeItem();


    }

    public void imageClicked (View view) {

    }

    public void changeItem () {
       FoodListRecyclerViewAdapter adapter = new FoodListRecyclerViewAdapter ();

        adapter.modifyItem(food.getId(), food);
        adapter.notifyDataSetChanged();
        firestoreMain.changeFood(food);
    }
    }






