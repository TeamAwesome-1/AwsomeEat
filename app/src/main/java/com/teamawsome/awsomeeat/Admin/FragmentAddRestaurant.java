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
import android.widget.Spinner;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.teamawsome.awsomeeat.Admin.*;
import com.teamawsome.awsomeeat.R;

public class FragmentAddRestaurant extends Fragment {
    private FirestoreMain firestoreMain;
    View view;


    public FragmentAddRestaurant() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.add_resturant_fragment,container,false);
        String [] values = {"French","Italian","Asian","Other"};
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        MaterialEditText restaurantName = view.findViewById(R.id.restaurantName);
        MaterialEditText restaurantAdress = view.findViewById(R.id.restaurantAdress);
        MaterialEditText restaurantPhoneNumber = view.findViewById(R.id.restaurantPhoneNumber);
        Button addRestButton = view.findViewById(R.id.btnAddRest);
        firestoreMain = FirestoreMain.getInstance();
        addRestButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                               String restName = restaurantName.getText().toString();
                                               String restAdress = restaurantAdress.getText().toString();
                                               String restPhone = restaurantPhoneNumber.getText().toString();
                                               firestoreMain.addRestaurant(restName, restAdress, restPhone);

                                             }
                                         }
        );
        return view;

    }



}
