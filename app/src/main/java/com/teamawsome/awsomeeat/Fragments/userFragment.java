package com.teamawsome.awsomeeat.Fragments;

import android.content.Context;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.teamawsome.awsomeeat.Admin.FirestoreMain;
import com.teamawsome.awsomeeat.Model.User;
import com.teamawsome.awsomeeat.R;

public class userFragment extends Fragment {
    private static final String TAG = "User";
    private FirestoreMain firestoreMain;
    private FirebaseFirestore db;
    private User userModel;
    private EditText adressText;
    private TextView display;
    private String adress = "test 123";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestoreMain = FirestoreMain.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //getUserDetails();
        //setUserDetails();

        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setTexttoView(dbAdress(), R.id.displayUserAdress);
        getActivity().findViewById(R.id.buttonUpdateInfo).setOnClickListener(this::sendDB);
    }

    private String dbAdress() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



    }

    private void sendDB(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            firestoreMain.setUserAdress(getinput(), user.getUid());
        }
    }

    private String getinput(){
        adressText = getView().findViewById(R.id.editAdress);
        return adressText.getText().toString();
    }

    private void setTexttoView(String text, int displayId){
        display = getView().findViewById(displayId);
        display.setText(text);
    }

    private void setUserDetails(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName("Test").build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d(TAG, "onComplete: User updated");

                                getUserDetails();
                            }
                        }
                    });
        }
    }

    private void getUserDetails(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            String uid = user.getUid();
            String name = user.getDisplayName();
            String email = user.getEmail();


            String properties = "uid: " + uid + " name " + name + " email " + email;

            Log.d(TAG, "Userdetails: " + properties);
        }
    }

    private void sendResetPassword(){
        FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: Reset Password Link sent");
                            Toast.makeText(getActivity(), "Sent Reset Password Link to Email", Toast.LENGTH_SHORT).show();
                        }else {
                            Log.d(TAG, "onComplete: No User with that email");
                            Toast.makeText(getActivity(), "No user with that email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
