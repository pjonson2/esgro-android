package com.upventrix.esgro.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
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
import com.google.gson.JsonObject;
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.Files;
import com.upventrix.esgro.modals.User;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.services.FilesService;
import com.upventrix.esgro.services.UserService;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
     CircleImageView circleImageView;

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText phone;
    ConstraintLayout constraintLayout;
    private UserService userService;
    String imgeUrl = "";
    int userID = 0;
    String name = "";
    String encodedImage = "";

    private final int SELECT_PHOTO = 1;
    byte[] b = null;

    Bundle bundle;
    private FilesService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
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
        userService = Config.getInstance().create(UserService.class);
        service = Config.getInstance().create(FilesService.class);
        circleImageView = findViewById(R.id.profile_image);
    }

    void setListeners(){
        updateCancelBtn.setOnClickListener(cancelAction);
        saveBtn.setOnClickListener(saveAction);
        newPost.setOnClickListener(newAction);
        handshake.setOnClickListener(handshakeAction);
        contact.setOnClickListener(contactUs);
        settings.setOnClickListener(settingsAction);
        circleImageView.setOnClickListener(uploadImageAction);
    }

    void setValues(){
        bundle = getIntent().getExtras();

        firstName.setText(bundle.getString("firstName"));
        lastName.setText(bundle.getString("lastName"));
        email.setText(bundle.getString("email"));
        phone.setText(bundle.getString("contact"));
        userID = bundle.getInt("user_id");
        name = bundle.getString("userName");
        System.out.println("userID   userID   "+userID);
        imgeUrl = bundle.getString("image");
        Bitmap bm = null;
        System.out.println("Image URL is a "+imgeUrl);
        if(imgeUrl.toString().equals("")){
            System.out.println("NUllllllllll image");
        }else {
            try {
                System.out.println("NOTTTTTT NULLll image");

                URL aURL = new URL(imgeUrl);
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {
                System.out.println("Error getting bitmap" + e);
            }
            circleImageView.setImageBitmap(bm);
        }
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
//                        profile.setImageBitmap(selectedImage);
                        circleImageView.setImageBitmap(selectedImage);
                        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
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
        saveBtn.setEnabled(false);
            User user = new User();
                user.setFirstname(firstName.getText().toString());
                user.setLastname(lastName.getText().toString());
                user.setEmail(email.getText().toString());
                user.setMobile(phone.getText().toString());
                user.setUsername(name);
                user.setUserID(userID);

                if (encodedImage.equals("") && imgeUrl.equals("")){
                    user.setImageUrl("");
                    Call<JsonObject> jsonObjectCall = userService.updateUSer(user);
                    jsonObjectCall.enqueue(new Callback<JsonObject>() {
                        String status= "";
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            status = response.body().get("status").getAsString();
                            if(status.toString().equals("success")){
                                vewAlert("Successfully","Profile Successfully Updated",UpdateProfileActivity.this);
                                saveBtn.setEnabled(true);
                            }else{
                                saveBtn.setEnabled(true);
                                vewAlert("Warnings!","Failed to update profile",UpdateProfileActivity.this);
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            System.out.println("ERROR");
                        }
                    });
                }

                if (encodedImage.equals("") && !imgeUrl.equals("")){
                    user.setImageUrl(imgeUrl);
                    Call<JsonObject> jsonObjectCall = userService.updateUSer(user);
                    jsonObjectCall.enqueue(new Callback<JsonObject>() {
                        String status= "";

                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            status = response.body().get("status").getAsString();
                            if(status.toString().equals("success")){
                                saveBtn.setEnabled(true);
                                vewAlert("Successfully","Profile Successfully Updated",UpdateProfileActivity.this);

                            }else{
                                saveBtn.setEnabled(true);
                                vewAlert("Warnings!","Failed to update profile",UpdateProfileActivity.this);
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            System.out.println("ERROR");
                        }
                    });

                }

                if (!encodedImage.equals("")){
                    Call<JsonObject> imgUrlFromBase64 = service.getImgUrlFromBase64(new Files(encodedImage));
                    imgUrlFromBase64.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                             try {
                                imgeUrl = response.body().get("filename").getAsString();

                                 user.setImageUrl(imgeUrl);
                                 System.out.println("THe image is  "+imgeUrl);

                                 Call<JsonObject> jsonObjectCall = userService.updateUSer(user);
                                 jsonObjectCall.enqueue(new Callback<JsonObject>() {
                                     String status= "";

                                     @Override
                                     public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                         status = response.body().get("status").getAsString();
                                         if(status.toString().equals("success")){
                                             vewAlert("Successfully","Profile Successfully Updated",UpdateProfileActivity.this);
                                             saveBtn.setEnabled(true);
                                         }else{
                                             saveBtn.setEnabled(true);
                                             vewAlert("Warnings!","Failed to update profile",UpdateProfileActivity.this);
                                         }
                                     }

                                     @Override
                                     public void onFailure(Call<JsonObject> call, Throwable t) {
                                         System.out.println("ERROR");
                                     }
                                 });


                             } catch (Exception e) {
                                 e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });
                }
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

    public void vewAlert(final String title, String message, final Context context){
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (title.equals("Successfully")) {
                            Intent mainIntent = new Intent(context, ProfileActivity.class);
                            context.startActivity(mainIntent);
                        }
                    }
                });
        alertDialog.show();
    }

}
