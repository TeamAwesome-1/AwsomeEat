package com.teamawsome.awsomeeat.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.teamawsome.awsomeeat.Database.FirestoreMain;
import com.teamawsome.awsomeeat.Helpers.PictureHandler;
import com.teamawsome.awsomeeat.R;

import java.util.List;

public class FragmentAddRestaurant extends Fragment {
    private FirestoreMain firestoreMain;
    private View view;
    private ImageView imageView;
    private EditText editText;
    private String restaurantPicString;
    private PictureHandler pictureHandler = PictureHandler.getInstance();



    public FragmentAddRestaurant() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.add_resturant_fragment,container,false);

        MaterialEditText restaurantName = view.findViewById(R.id.editrestaurantname_edittext);
        MaterialEditText restaurantAdress = view.findViewById(R.id.editrestaurantadress_edittext);
        MaterialEditText restaurantPhoneNumber = view.findViewById(R.id.restaurantPhoneNumber);
        Button addRestButton = view.findViewById(R.id.btnAddRest);
        firestoreMain = FirestoreMain.getInstance();
        imageView = view.findViewById(R.id.restaurantpic_imageview);
        editText = view.findViewById(R.id.restaurantpic_edittext);
        addRestButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 restaurantPicString = editText.getText().toString();
                                               String restName = restaurantName.getText().toString();
                                               String restAdress = restaurantAdress.getText().toString();
                                               String restPhone = restaurantPhoneNumber.getText().toString();
                                               if (restaurantPicString.contains("http")) {
                                                   pictureHandler.setPictureFromUrl(restaurantPicString, imageView);
                                                   firestoreMain.addRestaurant(restName, restAdress, restPhone, restaurantPicString);
                                                   Toast.makeText(getContext(), getString(R.string.the_restaurant_have_been_added), Toast.LENGTH_SHORT).show();
                                                   getActivity().getSupportFragmentManager().popBackStack();
                                               } else {
                                                   restaurantPicString = "https://firebasestorage.googleapis.com/v0/b/awsomeeat.appspot.com/o/rest111.jpg?alt=media&token=424c59be-723e-4ec1-babc-b569c0f45d8d";
                                                   pictureHandler.setPictureFromUrl(restaurantPicString, imageView);
                                                   firestoreMain.addRestaurant(restName, restAdress, restPhone, restaurantPicString);
                                                   getActivity().getSupportFragmentManager().popBackStack();
                                                   Toast.makeText(getContext(), getString(R.string.adding_a_restaurant_with_default_picture), Toast.LENGTH_SHORT).show();
                                               }
                                             }
                                         }
        );
        return view;

    }



}
