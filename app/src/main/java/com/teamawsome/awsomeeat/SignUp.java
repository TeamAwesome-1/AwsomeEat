package com.teamawsome.awsomeeat;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.teamawsome.DelayedProgressDialog;
import com.teamawsome.awsomeeat.Common.Common;
import com.teamawsome.awsomeeat.Database.Authentication;

public class SignUp extends AppCompatActivity {

    private MaterialEditText edtName,edtPassword,edtConfirmPassword;
    private static Authentication authentication = Authentication.getInstance();
    private Button btnSignUp;
    private static final String TAG = "Register";
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName = (MaterialEditText)findViewById(R.id.edtName);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtConfirmPassword= (MaterialEditText)findViewById(R.id.edtConfirmPassword);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(Common.isNetworkAvailable(getBaseContext())) {

                    if (edtName.getText().toString().isEmpty()){
                        Toast.makeText(SignUp.this, getString(R.string.not_empty_email), Toast.LENGTH_SHORT).show();
                    } else if (edtPassword.getText().toString().trim().length() < 6)
                        Toast.makeText(SignUp.this, getString(R.string.at_least_6_characters), Toast.LENGTH_SHORT).show();
                    else if (!(edtPassword.getText().toString()).equals(edtConfirmPassword.getText().toString()))
                        Toast.makeText(SignUp.this, getString(R.string.no_match_password), Toast.LENGTH_SHORT).show();
                    else {

                        authentication.registerEmail(edtName.getText().toString(), edtPassword.getText().toString(), SignUp.this);

                    }
            }
        }

        });

    }
}
