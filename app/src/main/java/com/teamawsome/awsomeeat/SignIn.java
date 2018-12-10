package com.teamawsome.awsomeeat;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.teamawsome.DelayedProgressDialog;
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

        authentication.setupFirebaseAuth(this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DelayedProgressDialog progressDialog = new DelayedProgressDialog();

                if (!edtEmail.getText().toString().isEmpty() &&
                        !edtPassword.getText().toString().isEmpty()){
                    Log.d("Login", "OnClick: attempting to authenticate.");
                    progressDialog.show(getSupportFragmentManager(), "Loading");

                    authentication.signIn(edtEmail.getText().toString(),
                            edtPassword.getText().toString(), context);
                }else{
                    Toast.makeText(SignIn.this, getString(R.string.didnt_enter_email_password), Toast.LENGTH_SHORT).show();
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
