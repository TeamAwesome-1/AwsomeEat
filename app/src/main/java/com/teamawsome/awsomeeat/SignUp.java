package com.teamawsome.awsomeeat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.teamawsome.awsomeeat.Common.Common;

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
