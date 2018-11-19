package com.teamawsome.awsomeeat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.teamawsome.awsomeeat.Model.User;
import com.teamawsome.awsomeeat.Common.Common;

public class SignIn extends AppCompatActivity {
    private EditText edtEmail,edtPassword;
    private Button btnSignIn;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtEmail = (MaterialEditText)findViewById(R.id.edtEmail);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);


        // FIREBASE
        setupFirebaseAuth();

        /*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user= database.getReference("User");
        */

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please Wait");

                if (!edtEmail.getText().toString().isEmpty() &&
                        !edtPassword.getText().toString().isEmpty()){
                    Log.d("Login", "OnClick: attempting to authenticate.");
                    mDialog.show();
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(edtEmail.getText().toString(),
                            edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mDialog.hide();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignIn.this, "Login failed!", Toast.LENGTH_SHORT).show();
                            mDialog.hide();
                        }
                    });
                }else{
                    Toast.makeText(SignIn.this, "You didn't enter email & password!", Toast.LENGTH_SHORT).show();
                }









/*

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //CHecking the use names from the database ...if use exists or not !
                        //Get User Information
                        if(dataSnapshot.child(edtEmail.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtEmail.getText().toString()).getValue(User.class);
                            user.setPhone(edtEmail.getText().toString());

                            if (user.getPassword().equals(edtPassword.getText().toString())) {
                                Intent homeIntent = new Intent(SignIn.this,Home.class);
                                Common.currentUser=user;
                                startActivity(homeIntent);
                                finish();
                            } else {
                                Toast.makeText(SignIn.this, "Wrong Phone number or Password", Toast.LENGTH_SHORT).show();
                            }
                        }


                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this,"User Doesnt exists in the database" ,Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/
            }
        });
        }

    private void setupFirebaseAuth(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d("Login", "onAuthStateChanged: signed_in: " + user.getUid());
                    Intent intent = new Intent(SignIn.this, AwsomeEatActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    Log.d("Login", "onAuthStateChanged: signed_out");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }
}
