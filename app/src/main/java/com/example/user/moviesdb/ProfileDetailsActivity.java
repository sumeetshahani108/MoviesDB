package com.example.user.moviesdb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.login.LoginManager;
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

            public String callingActivity;

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(firebaseAuth.getCurrentUser() == null){
                    Log.d(TAG, "not signed in ");
                    Bundle bundle = getIntent().getExtras();
                    callingActivity = bundle.getString("calling_activity");
                    Log.d(TAG, "" + callingActivity);
                    switch (callingActivity){

                    case ActivityConstants.ACTIVITY_3:
                        Log.d(TAG, "inside if ");
                        Intent loginIntent = new Intent(ProfileDetailsActivity.this, MainActivity.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);
                        finish();
                        break;
                    case ActivityConstants.ACTIVITY_4:
                        Log.d(TAG, "inside else ");
                        finish();
                        break;// if the user presses back button then it should go bak to home screen instead of looping
                    }
                }else{
                    Log.d(TAG, "signed in");
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    public void onClick(View v) {
        mAuth.signOut();
        Log.d(TAG, "inside logout" );
        if(getIntent().hasExtra("facebookLogin")){
            LoginManager.getInstance().logOut();
        }
        /*Intent mainactivityIntent = new Intent(ProfileDetailsActivity.this, MainActivity.class);
        mainactivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mainactivityIntent.putExtra("logout", true);
        startActivity(mainactivityIntent);*/
        finish();
    }
}
