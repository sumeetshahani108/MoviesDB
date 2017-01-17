package com.example.user.moviesdb;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";
    EditText name;
    EditText password;
    EditText confirmPassword;
    EditText email;

    Button register;

    ProgressDialog progessDialog;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        name = (EditText) findViewById(R.id.register_name);
        password = (EditText) findViewById(R.id.register_password);
        confirmPassword = (EditText) findViewById(R.id.register_confirm_password);
        email = (EditText) findViewById(R.id.register_email);
        register = (Button) findViewById(R.id.register_button);

        register.setOnClickListener(this);

        progessDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        startRegister();
    }

    private void startRegister() {
        String rName = name.getText().toString().trim();
        String rPassword = password.getText().toString().trim();
        String rConfirmPassword = confirmPassword.getText().toString().trim();
        String rEmail = email.getText().toString().trim();

        if (!TextUtils.isEmpty(rName) &&
                !TextUtils.isEmpty(rPassword) &&
                !TextUtils.isEmpty(rConfirmPassword) &&
                !TextUtils.isEmpty(rEmail)) {
            if (rPassword == rConfirmPassword) {

                progessDialog.setMessage("Signing Up...");
                progessDialog.show();

                mAuth.createUserWithEmailAndPassword(rEmail, rPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, " insiddecreateUserWithEmailAndPassword ");
                        }

                    }
                });
            }

        }


    }
}
