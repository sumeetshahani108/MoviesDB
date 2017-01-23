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
import android.widget.ImageButton;
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

import java.util.Set;

public class SetupAccountActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "SetupAccountActivity";
    private static final int GALLERY_REQUEST = 1;

    ImageButton profileImage;
    Uri imageUri = null;
    EditText name;
    EditText phoneNumber;
    RadioGroup choose_sex;
    RadioButton sex;
    Button register;
    ProgressDialog progessDialog;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private StorageReference mStorageImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_account);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mStorageImage = FirebaseStorage.getInstance().getReference().child("users");

        profileImage = (ImageButton) findViewById(R.id.account_image);
        phoneNumber = (EditText) findViewById(R.id.account_phone);

        choose_sex = (RadioGroup) findViewById(R.id.account_sex);

        choose_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sex = (RadioButton) findViewById(checkedId);

            }
        });
        register = (Button) findViewById(R.id.account_setup_button);
        profileImage.setOnClickListener(this);
        progessDialog = new ProgressDialog(this);

        register.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_image:
                getImage();
                break;
            case R.id.account_setup_button:
                startRegister();
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

    private void startRegister() {
        //Log.d(TAG, " inside startregister ");
        final String rName = name.getText().toString().trim();
        final String rPhoneNumber = phoneNumber.getText().toString().trim();
        final String rSex = sex.getText().toString().trim();

        if (!TextUtils.isEmpty(rName) &&
                !TextUtils.isEmpty(rPhoneNumber) &&
                !TextUtils.isEmpty(rSex) &&
                imageUri != null) {
            Log.d(TAG, " not empty ");
            progessDialog.setMessage("Setting up the account Up...");
            progessDialog.show();
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

                    Intent profileDetailsIntent = new Intent(SetupAccountActivity.this, ProfileDetailsActivity.class);
                    profileDetailsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(profileDetailsIntent);
                }
            });


        }

    }
}