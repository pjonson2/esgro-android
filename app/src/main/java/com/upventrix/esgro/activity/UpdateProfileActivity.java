package com.upventrix.esgro.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.upventrix.esgro.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class UpdateProfileActivity   extends AppCompatActivity {

    Button back;
    Button updateCancelBtn;
    Button saveBtn;

    ImageView newPost;
    ImageView handshake;
    ImageView contact;
    ImageView settings;
    ImageView profile;
    ImageView uploadImage;

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText phone;
    ConstraintLayout constraintLayout;
    private final int SELECT_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        idInitialization();
        setListeners();
        setValues();
        constraintLayout = findViewById(R.id.activity_update_profile);
        constraintLayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    void idInitialization(){
        updateCancelBtn = findViewById(R.id.updateCancelBtn);
        saveBtn = findViewById(R.id.updateSaveBtn);
        newPost = findViewById(R.id.updateProfileNewPostIcon);
        handshake = findViewById(R.id.updateProfileHandshakeIcon);
        contact = findViewById(R.id.updateProfileContactIcon);
        settings = findViewById(R.id.updateprofileSettingsIcon);
        firstName = findViewById(R.id.updateFirstNameTxt);
        lastName = findViewById(R.id.updateLastNameTxt);
        email = findViewById(R.id.updateEmailTxt);
        phone = findViewById(R.id.updatePhoneTxt);
        uploadImage = findViewById(R.id.imageView46);
        profile = findViewById(R.id.displayImage);
    }

    void setListeners(){
        updateCancelBtn.setOnClickListener(cancelAction);
        saveBtn.setOnClickListener(saveAction);
        newPost.setOnClickListener(newAction);
        handshake.setOnClickListener(handshakeAction);
        contact.setOnClickListener(contactUs);
        settings.setOnClickListener(settingsAction);
        uploadImage.setOnClickListener(uploadImageAction);
    }

    void setValues(){

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    View.OnClickListener uploadImageAction = new View.OnClickListener() {
        public void onClick(View v) {


            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);

        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);

                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        profile.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }
    View.OnClickListener cancelAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
            UpdateProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener saveAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
            UpdateProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener contactUs = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(UpdateProfileActivity.this,ContactUsActivity.class);
            UpdateProfileActivity.this.startActivity(mainIntent);
        }
    };

    View.OnClickListener settingsAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(UpdateProfileActivity.this,HomePageActivity.class);
            UpdateProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener handshakeAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(UpdateProfileActivity.this,DisputeNoHistoryActivity.class);
            UpdateProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener newAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(UpdateProfileActivity.this,RequestActivity.class);
            UpdateProfileActivity.this.startActivity(mainIntent);
        }
    };
}
