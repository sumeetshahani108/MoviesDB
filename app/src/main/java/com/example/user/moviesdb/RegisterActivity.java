package com.example.user.moviesdb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";
    private static final int GALLERY_REQUEST = 101;
    ImageButton profileImage;
    Bitmap image;
    Uri imageUri = null;
    EditText name;
    EditText phoneNumber;
    RadioGroup choose_sex;
    RadioButton sex;
    EditText password;
    EditText confirmPassword;
    EditText email;
    Button register;
    TextView login;

    String rPassword;
    String rConfirmPassword;
    String rEmail;
    String rName;
    String rPhoneNumber;
    String rSex;

    Uri uri;

    ProgressDialog progessDialog;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private StorageReference mStorageImage;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int GALLERY_INTENT = 2;
    //private ProgressBar textViewPasswordStrengthIndiactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mStorageImage = FirebaseStorage.getInstance().getReference().child("users");


        password = (EditText) findViewById(R.id.register_password);
        confirmPassword = (EditText) findViewById(R.id.register_confirm_password);
        email = (EditText) findViewById(R.id.register_email);

        name = (EditText) findViewById(R.id.register_name);
        profileImage = (ImageButton) findViewById(R.id.register_image);
        phoneNumber = (EditText) findViewById(R.id.register_phone_number);

        choose_sex = (RadioGroup) findViewById(R.id.register_choose_sex);

        choose_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sex = (RadioButton) findViewById(checkedId);

            }
        });
       // textViewPasswordStrengthIndiactor = (ProgressBar) findViewById(R.id.passwordCheckerProgressBar);
        login = (TextView) findViewById(R.id.already_registered) ;
        login.setOnClickListener(this);
        profileImage.setOnClickListener(this);
        progessDialog = new ProgressDialog(this);
        register = (Button) findViewById(R.id.register_next_button);
        register.setOnClickListener(this);

      /*  password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()==0)
                    textViewPasswordStrengthIndiactor.setProgress(0);
                else if(s.length()<6)
                    textViewPasswordStrengthIndiactor.setProgress(20);
                else if(s.length()<10)
                    textViewPasswordStrengthIndiactor.setProgress(40);
                else if(s.length()<15)
                    textViewPasswordStrengthIndiactor.setProgress(60);
                else
                    textViewPasswordStrengthIndiactor.setProgress(80);
                if(s.length()==20)
                    textViewPasswordStrengthIndiactor.setProgress(100);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!= null){
            final String user_email = mAuth.getCurrentUser().getEmail();
            email.setText(user_email);
            email.setFocusable(false);
            email.setEnabled(false);
            email.setCursorVisible(false);
            password.setFocusable(false);
            password.setEnabled(false);
            password.setCursorVisible(false);
            confirmPassword.setFocusable(false);
            confirmPassword.setEnabled(false);
            confirmPassword.setCursorVisible(false);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    Log.d("focus", "touchevent");
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_image:
                getImage();
                break;
            case  R.id.register_next_button:
                Log.d(TAG, "inside switch");
                startCreateAccount();
                break;
            case R.id.already_registered:
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
        }
    }
    private void getImage() {
        Intent galleryItent = new Intent(Intent.ACTION_PICK);
        galleryItent.putExtra("crop", "true");
        galleryItent.setType("image/*");
        startActivityForResult(galleryItent, GALLERY_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, " 1.requestCode " + requestCode);
        Log.d(TAG, " 1.resultCode " + resultCode);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            Log.d(TAG, " if ");
            Uri imageUri = data.getData();
            Log.d(TAG, "imageURi" + imageUri);
            profileImage.setImageURI(imageUri);

        } else {
            Log.d(TAG, " else ");

        }
    }

    private void startCreateAccount() {
        Log.d(TAG, "inside startCreateAccount");

        Bundle bundle = getIntent().getExtras();
        Log.d(TAG, "callingActiviity1" + bundle.getString("calling_activity"));
        rName = name.getText().toString().trim();
        rPhoneNumber = phoneNumber.getText().toString().trim();
        rSex = sex.getText().toString().trim();
        rEmail = email.getText().toString().trim();
        rPassword = password.getText().toString().trim();
        rConfirmPassword = confirmPassword.getText().toString().trim();
            Log.d(TAG, "" + rSex);
            Log.d(TAG, "" + rPassword);
            Log.d(TAG, "" + rConfirmPassword);
            Log.d(TAG, "" + rEmail);
            Log.d(TAG, "" + rName);
            Log.d(TAG, "" + rPhoneNumber);
            switch (bundle.getString("calling_activity")){
                case ActivityConstants.ACTIVITY_1:
                    Log.d(TAG, "inside switch activity1");
                    if (!TextUtils.isEmpty(rName) &&
                            !TextUtils.isEmpty(rPhoneNumber) &&
                            !TextUtils.isEmpty(rSex) &&
                            !TextUtils.isEmpty(rPassword) &&
                            !TextUtils.isEmpty(rConfirmPassword) &&
                            !TextUtils.isEmpty(rEmail)) {
                        if(rPassword.equals(rConfirmPassword)) {
                            progessDialog.setMessage("Signing Up...");
                            progessDialog.show();
                            mAuth.createUserWithEmailAndPassword(rEmail, rPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, " insidecreateUserWithEmailAndPassword ");
                                        insertIntoDatabase();

                                    }else {
                                        Log.d(TAG, " NOT insidecreateUserWithEmailAndPassword ");
                                    }

                                }
                            });
                        }else {
                            Toast.makeText(this,"Passwords do not match", Toast.LENGTH_LONG).show();
                        }

                    }else {
                        Toast.makeText(this,"Fill the details correctly", Toast.LENGTH_LONG).show();
                    }


                    break;
                case ActivityConstants.ACTIVITY_2:
                    Log.d(TAG, "inside switch activity1");
                    if (!TextUtils.isEmpty(rName) &&
                            !TextUtils.isEmpty(rPhoneNumber) &&
                            !TextUtils.isEmpty(rSex) ) {
                        progessDialog.setMessage("Signing Up...");
                        progessDialog.show();
                        insertIntoDatabase();
                    }else {
                        Log.d(TAG, "inside else");
                    break;

            }

        }

    }

    private void insertIntoDatabase() {
        Log.d(TAG, " inside insert into db ");
        final String user_id = mAuth.getCurrentUser().getUid();
        Log.d(TAG, "user_id" + user_id);
        StorageReference filepath = mStorageImage.child(imageUri.getLastPathSegment());
        Log.d(TAG, " Filepath " + filepath);
        filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
          Log.d(TAG, " Image Uploaded  ");
        String downloadUri = taskSnapshot.getDownloadUrl().toString();
        DatabaseReference current_user_id = mDatabase.child(user_id);
        current_user_id.child("profileImage").setValue(downloadUri);
        current_user_id.child("name").setValue(rName);
        current_user_id.child("sex").setValue(rSex);
        current_user_id.child("phone_number").setValue(rPhoneNumber);
        progessDialog.dismiss();
        Intent setupAccountIntent = new Intent(RegisterActivity.this, ProfileDetailsActivity.class);
        setupAccountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(setupAccountIntent);
         }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backPresssedIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(backPresssedIntent);
        finish();
    }
}
