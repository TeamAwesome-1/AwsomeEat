package com.teamawsome.awsomeeat.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.teamawsome.awsomeeat.Admin.FirestoreMain;
import com.teamawsome.awsomeeat.AwsomeEatActivity;
import com.teamawsome.awsomeeat.Database.Authentication;
import com.teamawsome.awsomeeat.Model.User;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.SignUpProfileFields;

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
                Intent intent = new Intent(getActivity(), AwsomeEatActivity.class);
                startActivity(intent);
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
