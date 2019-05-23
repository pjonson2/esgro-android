package com.upventrix.esgro.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.upventrix.esgro.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

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
    SimpleDraweeView userImg;

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText phone;
    ConstraintLayout constraintLayout;
    private final int SELECT_PHOTO = 1;
    byte[] b = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Fresco.initialize(this);
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
        userImg = findViewById(R.id.userImg);
    }

    void setListeners(){
        updateCancelBtn.setOnClickListener(cancelAction);
        saveBtn.setOnClickListener(saveAction);
        newPost.setOnClickListener(newAction);
        handshake.setOnClickListener(handshakeAction);
        contact.setOnClickListener(contactUs);
        settings.setOnClickListener(settingsAction);
        userImg.setOnClickListener(uploadImageAction);
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
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);


        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                                            final Uri imageUri = imageReturnedIntent.getData();
                    final InputStream imageStream;



                    try {
                        imageStream = getContentResolver().openInputStream(imageUri);

                          Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageBytes = baos.toByteArray();
                        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                        //decode base64 string to image
                        imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                        b = new byte[0];
//                        selectedImage.setWidth(profile.getWidth());
//                        selectedImage.setHeight(profile.getHeight());
                        b =  imageBytes;
                        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
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


//            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
//try {
////    Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/master/docs/static/logo.png");
////            SimpleDraweeView draweeView = (SimpleDraweeView) profile;
//    Uri imageUri = Uri.parse("https://www.gstatic.com/webp/gallery/1.jpg");
//    userImg.setController(
//            Fresco.newDraweeControllerBuilder()
//                    .setOldController(userImg.getController())
//                    .setUri(imageUri)
//                    .setTapToRetryEnabled(true)
//                    .build());
////    profile.setImageURI(uri);
//}catch(Exception e){
//
//}

            Intent mainIntent = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
            UpdateProfileActivity.this.startActivity(mainIntent);

//            Bitmap bm = null;
//            try {
//                URL aURL = new URL("https://raw.githubusercontent.com/facebook/fresco/master/docs/static/logo.png");
//                URLConnection conn = aURL.openConnection();
//                conn.connect();
//                InputStream is = conn.getInputStream();
//                BufferedInputStream bis = new BufferedInputStream(is);
//                bm = BitmapFactory.decodeStream(bis);
//                bis.close();
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            profile.setImageBitmap(bm);
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
