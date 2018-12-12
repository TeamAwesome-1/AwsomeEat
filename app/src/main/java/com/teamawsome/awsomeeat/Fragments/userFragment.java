package com.teamawsome.awsomeeat.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.teamawsome.awsomeeat.AwsomeEatActivity;
import com.teamawsome.awsomeeat.Database.Authentication;
import com.teamawsome.awsomeeat.R;

public class userFragment extends Fragment {
    private static final String TAG = "User";
    private static Authentication authentication = Authentication.getInstance();
    private EditText adressText;
    private TextView display;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       setTexttoView(authentication.getUserName(), R.id.editName);
       setTexttoView(authentication.getUserEmail(), R.id.displayEmail);
        setTexttoView(authentication.getAdress(), R.id.editAdress);
        getActivity().findViewById(R.id.buttonUpdateInfo).setOnClickListener(this::sendDB);
    }

    private void sendDB(View view){
        if (getinput(R.id.editAdress).isEmpty() ) {
            Toast.makeText(getActivity(), getString(R.string.must_enter_adress), Toast.LENGTH_SHORT).show();
        }else if (getinput(R.id.editName).isEmpty()){
            Toast.makeText(getActivity(), getString(R.string.must_enter_name), Toast.LENGTH_SHORT).show();
        }else{
            if (authentication.getCurrentUser() != null) {
                authentication.setUserAdress(getinput(R.id.editAdress), authentication.getCurrentUser().getUid());
                authentication.setUserName(getinput(R.id.editName));
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Bundle bundle = this.getArguments();
                String Info_key = bundle.getString("Info_key");
                if (Info_key.equals("Info")){
                     Intent intent = new Intent(getActivity(), AwsomeEatActivity.class);
                     startActivity(intent);
                    Toast.makeText(getActivity(), getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();
                }else{
                    Fragment fragment = new RestaurantListFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentinsertlayout, fragment);
                    fragmentTransaction.commit();
                    Toast.makeText(getActivity(), getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();
                    authentication.loadAuthData();
                }
            }
        }
    }

    private String getinput(int displayId){
        adressText = getView().findViewById(displayId);
        return adressText.getText().toString();
    }

    private void setTexttoView(String text, int displayId){
        display = getView().findViewById(displayId);
        display.setText(text);
    }

    @Override
    public void onResume() {
        super.onResume();
        authentication.checkAuthState(getActivity());
    }
}
