package com.example.user.moviesdb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

public class ProfileDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ProfileDetailsActivity";

    TextView getName;
    TextView getEmail;
    TextView getPhone;

    Button signoutButton;
    Button shareButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mDatabase;
    private StorageReference mStorageImage;
    private ValueEventListener mDatabaseListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        getName = (TextView) findViewById(R.id.get_name);
        getEmail = (TextView) findViewById(R.id.get_email);
        getPhone = (TextView) findViewById(R.id.get_phone);
        signoutButton = (Button) findViewById(R.id.signout_button) ;
        shareButton = (Button) findViewById(R.id.share_button);
        signoutButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener(){

            public String callingActivity;

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Bundle bundle = getIntent().getExtras();
                callingActivity = bundle.getString("calling_activity");
                if(firebaseAuth.getCurrentUser() == null){
                    Log.d(TAG, "not signed in ");
                    Log.d(TAG, "" + callingActivity);
                    switch (callingActivity){

                    case ActivityConstants.ACTIVITY_3:
                        Log.d(TAG, "inside if ");
                        Intent loginIntent = new Intent(ProfileDetailsActivity.this, MainActivity.class);
                        loginIntent.putExtra("calling_activity", ActivityConstants.ACTIVITY_5);
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
                    final String user_id = mAuth.getCurrentUser().getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
                    Log.d(TAG, "user id" +user_id);
                    Log.d(TAG, "user email" + mAuth.getCurrentUser().getEmail());
                    mDatabaseListener = mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d(TAG, callingActivity);
                            if(dataSnapshot.child(user_id).child("name").getValue() == null){
                                Log.d(TAG, "inside removeeventlistener  " + callingActivity);
                                mDatabase.removeEventListener(mDatabaseListener);
                            }else{
                                Log.d(TAG, callingActivity);
                                Log.d(TAG, "inside");
                                Log.d(TAG, "name" + dataSnapshot.child(user_id).child("name").getValue());
                                getName.setText(dataSnapshot.child(user_id).child("name").getValue().toString());
                                Log.d(TAG, "phone number" + dataSnapshot.child(user_id).child("phone_number").getValue());
                                getPhone.setText(dataSnapshot.child(user_id).child("phone_number").getValue().toString());
                                Log.d(TAG, "email" + mAuth.getCurrentUser().getEmail());
                                getEmail.setText(mAuth.getCurrentUser().getEmail());
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // mDatabase.removeEventListener(mDatabaseListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homescreenIntent = new Intent(ProfileDetailsActivity.this, HomeScreenActivity.class);
        homescreenIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homescreenIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signout_button:
                signOut();
                break;
            case R.id.share_button:
                share();
                break;
        }

    }

    private void share() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String downloadLink = "some download link";
        String message = "some message";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, message);
        shareIntent.putExtra(Intent.EXTRA_TEXT, downloadLink);
        startActivity(Intent.createChooser(shareIntent, "Share using"));
    }

    private void signOut() {
        mAuth.signOut();
        Log.d(TAG, "inside logout" );
        if(getIntent().hasExtra("facebookLogin")){
            LoginManager.getInstance().logOut();
        }
        Intent afterSignoutIntent = new Intent(ProfileDetailsActivity.this, HomeScreenActivity.class);
        afterSignoutIntent.putExtra("calling_activity",ActivityConstants.ACTIVITY_4);
        startActivity(afterSignoutIntent);
        finish();
    }
}
