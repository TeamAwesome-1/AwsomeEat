package com.teamawsome.awsomeeat;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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
import com.teamawsome.awsomeeat.Common.Common;
import com.teamawsome.awsomeeat.Model.User;

import static android.text.TextUtils.isEmpty;

public class SignUp extends AppCompatActivity {

    private MaterialEditText edtRoll,edtName,edtPassword,edtConfirmPassword;
    private Button btnSignUp;
    private FirebaseAuth mAuth;
    private static final String TAG = "Register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName = (MaterialEditText)findViewById(R.id.edtName);
        edtRoll = (MaterialEditText)findViewById(R.id.edtRoll);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtConfirmPassword= (MaterialEditText)findViewById(R.id.edtConfirmPassword);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        //Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        /*
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        */


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(Common.isNetworkAvailable(getBaseContext())) {
                    /*
                    if (!isEmpty(edtName.getText().toString()))
                        Toast.makeText(SignUp.this, "Email can not be empty!", Toast.LENGTH_SHORT).show();
*/
                    if (edtPassword.getText().toString().trim().length() < 3)
                        Toast.makeText(SignUp.this, "Password must have atleast 8 characters!", Toast.LENGTH_SHORT).show();
                    else if (!(edtPassword.getText().toString()).equals(edtConfirmPassword.getText().toString()))
                        Toast.makeText(SignUp.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    else {
                        registerEmail(edtName.getText().toString(), edtPassword.getText().toString());

                    }



                /*
                if(Common.isNetworkAvailable(getBaseContext())) {

                    if (edtRoll.getText().toString().trim().length() != 9)
                        Toast.makeText(SignUp.this, "Roll No. must have 9 characters!", Toast.LENGTH_SHORT).show();
                    else if (edtName.getText().toString().trim().length() < 3)
                        Toast.makeText(SignUp.this, "Name is too short!", Toast.LENGTH_SHORT).show();
                    else if (edtPassword.getText().toString().trim().length() < 8)
                        Toast.makeText(SignUp.this, "Password must have atleast 8 characters!", Toast.LENGTH_SHORT).show();
                    else if (!(edtPassword.getText().toString()).equals(edtConfirmPassword.getText().toString()))
                        Toast.makeText(SignUp.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    else {

                        final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                        mDialog.setMessage("Please Wait...");
                        mDialog.show();

                        table_user.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Check if userRoll already exist
                                if (dataSnapshot.child(edtRoll.getText().toString()).exists()) {
                                    mDialog.dismiss();
                                    Toast.makeText(SignUp.this, "User already registered!", Toast.LENGTH_SHORT).show();
                                } else {
                                    mDialog.dismiss();
                                    User user = new User(edtName.getText().toString(), edtPassword.getText().toString());
                                    table_user.child(edtRoll.getText().toString()).setValue(user);
                                    Toast.makeText(SignUp.this, "Sign up Successfull!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
                else
                {
                    Toast.makeText(SignUp.this, "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
                    return;
                }*/



            }
        }

        });

    }

    /*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    */

    private void registerEmail(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }

                        // ...
                    }
                });

    }
}
