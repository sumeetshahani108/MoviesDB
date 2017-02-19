package com.example.user.moviesdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "LoginActivity";
    EditText email;
    EditText password;

    ProgressDialog progressDialog;
    Button signin;
    TextView signUpNow;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        progressDialog = new ProgressDialog(this);
        signin = (Button) findViewById(R.id.signin_button);
        signUpNow = (TextView) findViewById(R.id.signUpNow);
        signUpNow.setOnClickListener(this);
        signin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signin_button:
                checkLogin();
                break;
            case R.id.signUpNow:
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
        }
    }

    private void checkLogin() {

        String rEmail = email.getText().toString().trim();
        String rPassword = password.getText().toString().trim();
        if (!TextUtils.isEmpty(rPassword) &&
                !TextUtils.isEmpty(rEmail)) {
            Log.d(TAG, "not empty");
            progressDialog.setMessage("Loging In");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);

            mAuth.signInWithEmailAndPassword(rEmail,rPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "success");
                        progressDialog.dismiss();
                        //Intent loginIntent = new Intent(LoginActivity.this,ProfileDetailsActivity.class);
                        //startActivity(loginIntent);
                        checkUserExists();
                    }else {
                        Log.d(TAG, "no success");
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Email or Password is incorrect",Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    private void checkUserExists() {
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(user_id)){

                    Intent loggedinIntent = new Intent(LoginActivity.this,ProfileDetailsActivity.class);
                    loggedinIntent.putExtra("calling_activity",ActivityConstants.ACTIVITY_2);
                    loggedinIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loggedinIntent);
                    finish();
                }else{
                    Intent setupIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                    setupIntent.putExtra("calling_activity",ActivityConstants.ACTIVITY_2);
                    setupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(setupIntent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
