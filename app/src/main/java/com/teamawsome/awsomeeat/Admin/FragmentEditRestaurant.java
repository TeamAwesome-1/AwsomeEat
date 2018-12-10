package com.teamawsome.awsomeeat.Admin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.teamawsome.awsomeeat.Model.Restaurant;
import com.teamawsome.awsomeeat.PictureHandler;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.idHolder;


public class FragmentEditRestaurant extends Fragment {
     private FirestoreMain firestoreMain;
    View view;
    private ImageView imageView;
    private EditText restpicUrl;
    private Restaurant restaurant;
    private MaterialEditText restaurantName;
    private MaterialEditText restaurantAdress;
    private MaterialEditText restaurantPhoneNumber;
    private Button updateRestaurantButton;



    public FragmentEditRestaurant() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_fragment_edit_restaurant,container,false);
        restaurantName = view.findViewById(R.id.editrestaurantname_edittext);
        restaurantAdress = view.findViewById(R.id.editrestaurantadress_edittext);
        restaurantPhoneNumber = view.findViewById(R.id.editrestaurantphone_edittext);
        updateRestaurantButton = view.findViewById(R.id.updaterestaurant_button);
        restaurant = idHolder.getRestaurant();
        imageView = view.findViewById(R.id.editrestaurantpic_imageview);
        PictureHandler.setPictureFromUrl(restaurant.getPictureUrl() , imageView);
        firestoreMain = FirestoreMain.getInstance();
        restpicUrl = view.findViewById(R.id.editrestaurantpicurl_edittext);
        restpicUrl.setText(restaurant.pictureUrl);
        restaurantName.setText(restaurant.getName());
        restaurantAdress.setText(restaurant.getAdress());
        restaurantPhoneNumber.setText(restaurant.getPhoneNumber());
        updateRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRestaurant();
            }
        });

        return view;

    }


    public void updateRestaurant() {
       restaurant.setName(restaurantName.getText().toString());
       restaurant.setAdress(restaurantAdress.getText().toString());
       restaurant.setPhoneNumber(restaurantPhoneNumber.getText().toString());
       restaurant.setPictureUrl(restpicUrl.getText().toString());
       PictureHandler.setPictureFromUrl(restpicUrl.getText().toString() , imageView);
       if (restpicUrl.getText().toString().contains("http") && restaurantName.getText().toString().length() > 5 && restaurantAdress.getText()
               .toString().length() >5 && restaurantPhoneNumber.getText().toString().contains("0")) {
            firestoreMain.updateRestaurant(restaurant.getId(), restaurant);
            Toast.makeText(this.getContext(), getString(R.string.the_restaurant_have_been_updated), Toast.LENGTH_SHORT).show();
        } else {
           Toast.makeText(this.getContext(), getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show();
        }
    }


}
