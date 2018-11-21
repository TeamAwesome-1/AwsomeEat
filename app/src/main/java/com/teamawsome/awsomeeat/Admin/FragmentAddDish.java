package com.teamawsome.awsomeeat.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.teamawsome.awsomeeat.R;

public class FragmentAddDish extends Fragment {
    View view;

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



        return view;

    }
}

