package com.example.user.moviesdb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ProfileDetailsActivity";

    Button signoutButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        signoutButton = (Button) findViewById(R.id.signout_button) ;
        signoutButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
       // Log.d(TAG, "user email" + mAuth.getCurrentUser().getEmail());
        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(firebaseAuth.getCurrentUser() == null){
                    Log.d(TAG, "not signed in ");
                    Intent loginIntent = new Intent(ProfileDetailsActivity.this, MainActivity.class);
                    startActivity(loginIntent);
                }else{
                    Log.d(TAG, "signed in");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    public void onClick(View v) {
        mAuth.signOut();
        Intent mainactivityIntent = new Intent(ProfileDetailsActivity.this, MainActivity.class);
        mainactivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainactivityIntent);
    }
}
