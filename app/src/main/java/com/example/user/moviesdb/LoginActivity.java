package com.example.user.moviesdb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText email;
    EditText password;

    Button signin;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);

        signin = (Button) findViewById(R.id.signin_button);

        signin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        checkLogin();
    }

    private void checkLogin() {

        String rEmail = email.getText().toString().trim();
        String rPassword = password.getText().toString().trim();
        if (!TextUtils.isEmpty(rPassword) &&
                !TextUtils.isEmpty(rEmail)) {

            mAuth.signInWithEmailAndPassword(rEmail,rPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent loggedinIntent = new Intent(LoginActivity.this,ProfileDetailsActivity.class);
                        loggedinIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loggedinIntent);
                    }else {
                        Toast.makeText(LoginActivity.this, "Could not log in",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
