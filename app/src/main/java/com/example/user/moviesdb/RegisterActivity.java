package com.example.user.moviesdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";
    private static final int GALLERY_REQUEST = 1;

    ImageButton profileImage;
    Uri imageUri = null;
    EditText name;
    EditText phoneNumber;
    RadioGroup choose_sex;
    RadioButton sex;
    EditText password;
    EditText confirmPassword;
    EditText email;
    Button register;

    ProgressDialog progessDialog;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private StorageReference mStorageImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mStorageImage = FirebaseStorage.getInstance().getReference().child("users");

        name = (EditText) findViewById(R.id.register_name);
        profileImage = (ImageButton) findViewById(R.id.register_image);
        phoneNumber = (EditText) findViewById(R.id.register_phone);

        choose_sex = (RadioGroup) findViewById(R.id.register_choose_sex);

        choose_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sex = (RadioButton) findViewById(checkedId);

            }
        });
        password = (EditText) findViewById(R.id.register_password);
        confirmPassword = (EditText) findViewById(R.id.register_confirm_password);
        email = (EditText) findViewById(R.id.register_email);
        register = (Button) findViewById(R.id.register_next_button);

        register.setOnClickListener(this);
        profileImage.setOnClickListener(this);
        progessDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_image:
                getImage();
                break;
            case  R.id.register_next_button:
                startCreateAccount();
                break;
        }
    }
    private void getImage() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, " 1.requestCode " + requestCode);
        Log.d(TAG, " 1.resultCode " + resultCode);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            Log.d(TAG, " if ");
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

        } else {
            Log.d(TAG, " else ");

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Log.d(TAG, " 2.requestCode " + requestCode);
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                profileImage.setImageURI(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Log.d(TAG, " 2.requestCode " + resultCode);
                Exception error = result.getError();
            }
        }
    }
    private void startCreateAccount() {
        final String rName = name.getText().toString().trim();
        final String rPhoneNumber = phoneNumber.getText().toString().trim();
        final String rSex = sex.getText().toString().trim();
        final String rPassword = password.getText().toString().trim();
        String rConfirmPassword = confirmPassword.getText().toString().trim();
        final String rEmail = email.getText().toString().trim();

        if ( !TextUtils.isEmpty(rPassword) &&
                !TextUtils.isEmpty(rConfirmPassword) &&
                !TextUtils.isEmpty(rEmail) &&
                !TextUtils.isEmpty(rName) &&
                !TextUtils.isEmpty(rPhoneNumber) &&
                !TextUtils.isEmpty(rSex) )  {
            Log.d(TAG, " not empty ");
            progessDialog.setMessage("Signing Up...");
            progessDialog.show();

            mAuth.createUserWithEmailAndPassword(rEmail, rPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, " insidecreateUserWithEmailAndPassword ");
                        final String user_id = mAuth.getCurrentUser().getUid();

                        StorageReference filepath = mStorageImage.child(imageUri.getLastPathSegment());
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

                }
            });


        }


    }
}
