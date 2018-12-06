package com.teamawsome.awsomeeat;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.teamawsome.awsomeeat.Database.Authentication;

public class SignIn extends AppCompatActivity {
    private EditText edtEmail,edtPassword;
    private Button btnSignIn;
    private static Authentication authentication = Authentication.getInstance();
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtEmail = (MaterialEditText)findViewById(R.id.edtEmail);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        authentication.setupFirebaseAuth(context);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please Wait");

                if (!edtEmail.getText().toString().isEmpty() &&
                        !edtPassword.getText().toString().isEmpty()){
                    Log.d("Login", "OnClick: attempting to authenticate.");
                    mDialog.show();
                    authentication.signIn(edtEmail.getText().toString(),
                            edtPassword.getText().toString());
                }else{
                    Toast.makeText(SignIn.this, "You didn't enter email & password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }

    @Override
    protected void onStart() {
        super.onStart();
        authentication.addStateListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        authentication.removeStateListener();
    }
}
