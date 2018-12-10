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
import com.teamawsome.awsomeeat.PictureHandler;
import com.teamawsome.awsomeeat.R;

public class FragmentAddRestaurant extends Fragment {
    private FirestoreMain firestoreMain;
    View view;
    private ImageView imageView;
    private EditText editText;



    public FragmentAddRestaurant() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.add_resturant_fragment,container,false);
        String [] values = {"French","Italian","Asian","Other"};
        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
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
                                                 String restaurantPicString = editText.getText().toString();
                                                 PictureHandler.setPictureFromUrl(restaurantPicString, imageView);

                                               String restName = restaurantName.getText().toString();
                                               String restAdress = restaurantAdress.getText().toString();
                                               String restPhone = restaurantPhoneNumber.getText().toString();
                                               firestoreMain.addRestaurant(restName, restAdress, restPhone, restaurantPicString);
                                                 Toast.makeText(getContext(), getString(R.string.the_restaurant_have_been_added), Toast.LENGTH_SHORT).show();
                                                 getActivity().getSupportFragmentManager().popBackStack();

                                             }
                                         }
        );
        return view;

    }



}
