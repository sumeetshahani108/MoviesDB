package com.example.user.moviesdb;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActvity";
    LoginButton facebookLoginButton; /* LoginButton for facebook*/
    SignInButton googleButton; /* login button for google*/
    Button loginButton; /* login with email and password*/
    Button signinButton; /* Register new account*/

    TextView notForNow;

    ProgressDialog progressDialog;

    /*
    to access firebase auuthentication
     */
    FirebaseAuth mAuth;
    /*
    to access firebase database
     */
    private DatabaseReference mDatabase;
    /*
    to access storage area for storing images or other files
     */
    private StorageReference mStorageImage;
    /*
    to connect with google services
     */
    GoogleApiClient googleApiClient;
    /*
    to response to a result need to add callback to the facebookLoginButton
     */
    CallbackManager mCallbackManager;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final int RC_SIGN_IN = 9001;
    private static final int FACEBOOK_REQUEST_CODE = 64206;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //facebook signin
        FacebookSdk.sdkInitialize(getApplicationContext());
        /*
        gets the analytics
         */
        //AppEventsLogger.activateApp(getApplication());
        mCallbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        Log.d(TAG, "AAGAAYA");

       /* if(getIntent().hasExtra("logout")){
            LoginManager.getInstance().logOut();
        }*/

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "if");
                    finish();
                } else {
                    Log.d(TAG, "else");
                }
            }
        };


        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        facebookLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        facebookLoginButton.setReadPermissions("email", " public_profile");
        facebookLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
                Log.d(TAG, "loginresult" + loginResult);

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "oncancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onerror");
            }
        });


        googleButton = (SignInButton) findViewById(R.id.google_button);
        loginButton = (Button) findViewById(R.id.login_button);
        signinButton = (Button) findViewById(R.id.signup_button);

        progressDialog = new ProgressDialog(this);
        /*
        get access to usersemail and public profile
         */
        googleButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        signinButton.setOnClickListener(this);

        // google signin
        /*
        - gets the signin options and using requestEmail - request the email of the user
        - token is used to prevent 3rd party users from accessing user data because email address can be guessed easily
         */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        /*
        connect to google api and pass in the signin optionsas the parameter
         */
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        notForNow = (TextView) findViewById(R.id.not_for_now);
        notForNow.setOnClickListener(this);

    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        progressDialog.setMessage("Signin in....");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        Log.d(TAG, "handleFacebookAccessToken");
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        Log.d(TAG, "access token" + credential);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential" + task.isSuccessful());
                        // finish();
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "not success");
                            Log.d(TAG, "signInWithCredential" + task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "success");
                            //finish();
                           /* Intent facebookIntent = new Intent(MainActivity.this, ProfileDetailsActivity.class);
                            facebookIntent.putExtra("facebookLogin", true);
                            startActivity(facebookIntent);
                            finish();*/
                            checkUserExist();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backPressedIntent = new Intent(MainActivity.this, HomeScreenActivity.class);
        backPressedIntent.putExtra("calling_activity", ActivityConstants.ACTIVITY_4);
        startActivity(backPressedIntent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
                break;
            case R.id.signup_button:
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                registerIntent.putExtra("calling_activity", ActivityConstants.ACTIVITY_1);
                startActivity(registerIntent);
                finish();
                break;
            case R.id.google_button:
                signin();
                break;
            case R.id.not_for_now:
                Intent homescreenIntent = new Intent(MainActivity.this, HomeScreenActivity.class);
                homescreenIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homescreenIntent);
                finish();
                break;
        }
    }

    private void signin() {
        /*
        gets the signin Itnet that lets the user insert email and password
         */
        Log.d(TAG, "inside signin");
        Intent signinIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signinIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivity result" + requestCode);
        if (requestCode == RC_SIGN_IN && data!=null) {
            Log.d(TAG, "requestcode" + requestCode);
            progressDialog.setMessage("Signin in with google....");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                Log.d(TAG, "if");
                /*
                access account details of user using getSignInAccount which gets the details of the user who currently signed in
                 */
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Log.d(TAG, "else");

            }
        } else if (requestCode == FACEBOOK_REQUEST_CODE) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);

        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        /*
        token is used to prevent 3rd party users.
        - at every login time a unique token is sent to google api to identify the user and then log it in
         */
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            //Intent googleIntent = new Intent(MainActivity.this,ProfileDetailsActivity.class);
                            //googleIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //startActivity(googleIntent);
                            checkUserExist();
                        }

                        // ...
                    }
                });
    }

    private void checkUserExist() {
        Log.d(TAG, "checkuserexist");
        if (mAuth.getCurrentUser() != null) {
            Log.d(TAG, "checkuserexist - if");
            final String user_id = mAuth.getCurrentUser().getUid();
            Log.d(TAG, "user_id" + user_id + " " + mAuth.getCurrentUser().getEmail());
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(user_id)) {
                        Log.d(TAG, "checkuserexist-if-if");
                       finish();
                       Intent profileIntent = new Intent(MainActivity.this, ProfileDetailsActivity.class);
                        profileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(profileIntent);

                    } else {
                        Log.d(TAG, "checkuserexist-if-else");
                        finish();
                        Intent setupAccountIntent = new Intent(MainActivity.this,RegisterActivity.class);
                        setupAccountIntent.putExtra("calling_activity",ActivityConstants.ACTIVITY_2);
                        //setupAccountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(setupAccountIntent);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

}
