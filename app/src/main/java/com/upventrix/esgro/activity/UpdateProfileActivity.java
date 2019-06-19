package com.upventrix.esgro.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.JsonObject;
import com.hbb20.CountryCodePicker;
import com.rafaelbarbosatec.archivimentview.AchievementView;
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.Files;
import com.upventrix.esgro.modals.User;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.Validations;
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
import java.util.concurrent.TimeUnit;

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
    String finish = "no";
    private final int SELECT_PHOTO = 1;
    byte[] b = null;

    Bundle bundle;
    private FilesService service;
    CountryCodePicker ccp;
    Drawable background;
    private String selectedCountryCode;

    Dialog dialog;
    String contactNUmber = "";
    int verificationId = 0;

    private static int logicNumber = 0;
    AchievementView achievementView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         requestWindowFeature(Window.FEATURE_NO_TITLE);
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
                        onWindowFocusChanged(true);

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                return false;
            }
        });
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                selectedCountryCode = ccp.getSelectedCountryCode();
                System.out.println(" selectedCountryCode  "+selectedCountryCode);
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
        ccp = findViewById(R.id.ccp);
        dialog = new Dialog(this);
        achievementView = findViewById(R.id.achievementView);
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
        background = email.getBackground();
        firstName.setText(bundle.getString("firstName"));
        lastName.setText(bundle.getString("lastName"));
        email.setText(bundle.getString("email"));

        ccp.registerCarrierNumberEditText(phone);
        ccp.setFullNumber( bundle.getString("contact"));
        contactNUmber =  bundle.getString("contact");


        userID = bundle.getInt("user_id");
        name = bundle.getString("userName");
        System.out.println("userID   userID   "+userID);
        imgeUrl = bundle.getString("image");
        Bitmap bm = null;
        System.out.println("Image URL is a "+imgeUrl);
        if(imgeUrl.toString().equals("")){
            System.out.println("NUlL image");
        }else {
            try {
                System.out.println("NoT NULL image");

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


    Window window_local;
    View.OnClickListener saveAction = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void onClick(View v) {
        saveBtn.setEnabled(false);
            User user = new User();
                user.setFirstname(firstName.getText().toString());
                user.setLastname(lastName.getText().toString());
                user.setEmail(email.getText().toString());
                user.setMobile(phone.getText().toString());
                user.setUsername(name);
                user.setUserID(userID);
                user.setMobile(ccp.getFullNumberWithPlus());


            boolean emailValid = new Validations().isEmailValid(email.getText().toString());

            if (emailValid) {
                email.setBackground(background);
            }else{
                ShapeDrawable shape = new ShapeDrawable(new RectShape());
                shape.getPaint().setColor(Color.RED);
                shape.getPaint().setStyle(Paint.Style.STROKE);
                shape.getPaint().setStrokeWidth(1);
                email.setBackground(shape);
                saveBtn.setEnabled(true);
                return;
            }


            if (contactNUmber.equals(ccp.getFullNumberWithPlus())){
                user.setMobile(ccp.getFullNumberWithPlus());
                logicNumber = 1;
//                System.out.println(" ccp.getFullNumberWithPlus()  "+ccp.getFullNumberWithPlus());
             }else{
                System.out.println("userService.verify api ready to call  "+ ccp.getFullNumberWithPlus());
                Call<JsonObject> userCall = userService.verify(
                        new User(
                                ccp.getFullNumberWithPlus(),
                                userID
                        ));
                userCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        String status1 = "";
                        System.out.println("userService.verify api calling");
                        try {
                            status1 = response.body().get("status").getAsString();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (status1.equals("success")) {
                            System.out.println("userService.verify api called");
                            verificationId = response.body().get("verification_id").getAsInt();

                            dialog.setContentView(R.layout.activity_verification_of_update_profile);
                            dialog.show();

                            dialog.setCanceledOnTouchOutside(false);
                            Window window = dialog.getWindow();
                            window_local = window;
                            Button verify = window.findViewById(R.id.verifyBtn);
                            Button cancel = window.findViewById(R.id.cancelBtn);
                            TextView otp = window.findViewById(R.id.otpBtn);
                            TextView timerView = window.findViewById(R.id.enterTimerLbl2);
                            new CountDownTimer(120000, 1000) { // adjust the milli seconds here

                                public void onTick(long millisUntilFinished) {
                                    timerView.setText(""+String.format("%d:%d",
                                            TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                                }

                                public void onFinish() {
                                    System.out.println("DONE");
                                    finish = "finished";
                                }
                            }.start();


                                    ((EditText)window.findViewById(R.id.enterNumber1Txt2)).addTextChangedListener(n1Change);
                            ((EditText)window.findViewById(R.id.enterNumber2Txt2)).addTextChangedListener(n2Change);
                            ((EditText)window.findViewById(R.id.enterNumber3Txt2)).addTextChangedListener(n3Change);

                            otp.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    if(finish.equals("finished")){
                                        finish = "no";
                                        callApi();
                                        new CountDownTimer(120000, 1000) { // adjust the milli seconds here

                                            public void onTick(long millisUntilFinished) {
                                                timerView.setText(""+String.format("%d:%d",
                                                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                                            }

                                            public void onFinish() {
                                                System.out.println("DONE");
                                                finish = "finished";
                                            }
                                        }.start();
                                    }
                                }
                            });

                            cancel.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      dialog.dismiss();
                                      logicNumber = 0;
                                      saveBtn.setEnabled(true);
                                      return;
                                  }
                            });
                            verify.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Window window = dialog.getWindow();
                                    verify.setEnabled(false);
                                    if (((EditText) window.findViewById(R.id.enterNumber1Txt2)).getText().toString().equals("") || ((EditText) window.findViewById(R.id.enterNumber2Txt2)).getText().toString().equals("") || ((EditText) window.findViewById(R.id.enterNumber3Txt2)).getText().toString().equals("") || ((EditText) window.findViewById(R.id.enterNumber4Txt2)).getText().toString().equals("")) {

                                        ShapeDrawable shape = new ShapeDrawable(new RectShape());
                                        shape.getPaint().setColor(Color.RED);
                                        shape.getPaint().setStyle(Paint.Style.STROKE);
                                        shape.getPaint().setStrokeWidth(1);

                                        if (((EditText) window.findViewById(R.id.enterNumber1Txt2)).getText().toString().equals("") ){((EditText) window.findViewById(R.id.enterNumber1Txt2)).setBackground(shape);}else{((EditText) window.findViewById(R.id.enterNumber1Txt2)).setBackground(background);}
                                        if(((EditText) window.findViewById(R.id.enterNumber2Txt2)).getText().toString().equals("")){((EditText) window.findViewById(R.id.enterNumber2Txt2)).setBackground(shape);}else{((EditText) window.findViewById(R.id.enterNumber2Txt2)).setBackground(background);}
                                        if(((EditText) window.findViewById(R.id.enterNumber3Txt2)).getText().toString().equals("")){((EditText) window.findViewById(R.id.enterNumber3Txt2)).setBackground(shape);}else{((EditText) window.findViewById(R.id.enterNumber3Txt2)).setBackground(background);}
                                        if(((EditText) window.findViewById(R.id.enterNumber4Txt2)).getText().toString().equals("")){((EditText) window.findViewById(R.id.enterNumber4Txt2)).setBackground(shape);}else{((EditText) window.findViewById(R.id.enterNumber4Txt2)).setBackground(background);}
                                    } else {
                                        ((EditText) window.findViewById(R.id.enterNumber1Txt2)).setBackground(background);
                                        ((EditText) window.findViewById(R.id.enterNumber2Txt2)).setBackground(background);
                                        ((EditText) window.findViewById(R.id.enterNumber3Txt2)).setBackground(background);
                                        ((EditText) window.findViewById(R.id.enterNumber4Txt2)).setBackground(background);

                                        int pin = Integer.parseInt(
                                            ((EditText) window.findViewById(R.id.enterNumber1Txt2)).getText() + "" +
                                                    ((EditText) window.findViewById(R.id.enterNumber2Txt2)).getText() + "" +
                                                    ((EditText) window.findViewById(R.id.enterNumber3Txt2)).getText() + "" +
                                                    ((EditText) window.findViewById(R.id.enterNumber4Txt2)).getText());

                                    Call<JsonObject> userCall = userService.confirm(
                                            new User(
                                                    verificationId,
                                                    pin
                                            ));
                                    userCall.enqueue(new Callback<JsonObject>() {
                                        @Override
                                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                            String status2 = "";

                                            try {
                                                status2 = response.body().get("status").getAsString();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            if (status2.equals("success")) {
                                                saveBtn.setEnabled(false);
                                                contactNUmber = ccp.getFullNumberWithPlus();
                                                logicNumber = 1;
                                                dialog.dismiss();
                                                callAPi(user);
                                            } else {
                                                new  ToastActivity().showFailed(
                                                        achievementView,
                                                        "Invalid!",
                                                        "Failed to verifying your code !"
                                                );
                                                saveBtn.setEnabled(true);
                                                verify.setEnabled(true);
                                                logicNumber = 0;
                                                return;
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<JsonObject> call, Throwable t) {
                                            saveBtn.setEnabled(true);
                                            verify.setEnabled(true);
                                            logicNumber = 0;
                                        }
                                    });
                                    System.out.println("Dismissed");
                                    return;
                                }
                                }
                            });

                        }else{
                            logicNumber = 0;
                            new  ToastActivity().showFailed(
                                    achievementView,
                                    "Invalid!",
                                    "Failed to Verify this Number !"
                            );
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        saveBtn.setEnabled(true);
                        return;
                    }
                });
            }

        callAPi(user);

        }
    };

    private void callApi() {
        Call<JsonObject> userCall = userService.verify(
                new User(
                        ccp.getFullNumberWithPlus(),
                        userID
                ));
        userCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String status1 = "";
                System.out.println("userService.verify api calling");
                try {
                    status1 = response.body().get("status").getAsString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (status1.equals("success")) {
                    verificationId = response.body().get("verification_id").getAsInt();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }

        });
    }

    private void callAPi(User user){
         if (logicNumber == 0){
            saveBtn.setEnabled(true);
        }
        if (encodedImage.equals("") && imgeUrl.equals("") && logicNumber == 1){
            System.out.println("This is Calling Method 1");
            user.setImageUrl("");
            Call<JsonObject> jsonObjectCall = userService.updateUSer(user);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                String status= "";
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    status = response.body().get("status").getAsString();
                    if(status.toString().equals("success")){
                        logicNumber = 0;
                         saveBtn.setEnabled(true);
                        new  ToastActivity().showOK(
                                achievementView,
                                "Successfully!",
                                "Profile Successfully Updated",
                                UpdateProfileActivity.this,
                                ProfileActivity.class
                        );
                    }else{
                        saveBtn.setEnabled(true);
                        new  ToastActivity().showFailed(
                                achievementView,
                                "Warnings!",
                                "Failed to update profile!"
                        );
                     }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    System.out.println("ERROR");saveBtn.setEnabled(true);
                }
            });
        }

        if (encodedImage.equals("") && !imgeUrl.equals("") && logicNumber == 1){
            System.out.println("This is Calling Method 2");
            user.setImageUrl(imgeUrl);
            Call<JsonObject> jsonObjectCall = userService.updateUSer(user);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                String status= "";

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    status = response.body().get("status").getAsString();
                    if(status.toString().equals("success")){
                        saveBtn.setEnabled(true);
                        logicNumber = 0;
                         new  ToastActivity().showOK(
                                achievementView,
                                "Successfully!",
                                "Profile Successfully Updated",
                                 UpdateProfileActivity.this,
                                 ProfileActivity.class
                        );

                    }else{
                        saveBtn.setEnabled(true);
                        new  ToastActivity().showFailed(
                                achievementView,
                                "Warnings!",
                                "Failed to update profile!"
                        );
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    System.out.println("ERROR");saveBtn.setEnabled(true);
                }
            });

        }

        if (!encodedImage.equals("") && logicNumber == 1){
            System.out.println("This is Calling Method 3");
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
                                     saveBtn.setEnabled(true);
                                    logicNumber = 0;
                                    new  ToastActivity().showOK(
                                            achievementView,
                                            "Successfully!",
                                            "Profile Successfully Updated",
                                            UpdateProfileActivity.this,
                                            ProfileActivity.class
                                    );
                                }else{
                                    saveBtn.setEnabled(true);
                                    new  ToastActivity().showFailed(
                                            achievementView,
                                            "Warnings!",
                                            "Failed to update profile!"
                                    );
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                System.out.println("ERROR");saveBtn.setEnabled(true);
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


    View.OnClickListener verifyNumbers = new View.OnClickListener() {
        public void onClick(View v) {
            Window window = dialog.getWindow();

            int pin = Integer.parseInt(
                    ((EditText)window.findViewById(R.id.enterNumber1Txt2)).getText() + "" +
                            ((EditText)window.findViewById(R.id.enterNumber2Txt2)).getText() + "" +
                            ((EditText)window.findViewById(R.id.enterNumber3Txt2)).getText() + "" +
                            ((EditText)window.findViewById(R.id.enterNumber4Txt2)).getText());

            Call<JsonObject> userCall = userService.confirm(
                    new User(
                            verificationId,
                            pin
                    ));
            userCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    String status = null;

                    try {
                        status = response.body().get("status").getAsString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (status.equals("success")){
                        System.out.println("///////////// / ///  Verified ");
                    }else{
                        new  ToastActivity().showFailed(
                                achievementView,
                                "Invalid!",
                                "Failed to verifying your code !"
                        );
                        saveBtn.setEnabled(true);
                        return;
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });


            System.out.println("Dismissed");
            return;
        }

    };
    TextWatcher n1Change = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!((EditText)window_local.findViewById(R.id.enterNumber1Txt2)).getText().toString().equals("")){
                ((EditText)window_local.findViewById(R.id.enterNumber2Txt2)).requestFocus();
            }

        }
    };

    TextWatcher n2Change = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!((EditText)window_local.findViewById(R.id.enterNumber2Txt2)).getText().toString().equals("")){
                ((EditText)window_local.findViewById(R.id.enterNumber3Txt2)).requestFocus();
            }

        }
    };

    TextWatcher n3Change = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!((EditText)window_local.findViewById(R.id.enterNumber3Txt2)).getText().toString().equals("")){
                ((EditText)window_local.findViewById(R.id.enterNumber4Txt2)).requestFocus();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    };
//
//    public void vewAlert(final String title, String message, final Context context){
//        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//        alertDialog.setTitle(title);
//        alertDialog.setMessage(message);
//        alertDialog.setCanceledOnTouchOutside(false);
//        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        if (title.equals("Successfully")) {
//                            Intent mainIntent = new Intent(context, ProfileActivity.class);
//                            context.startActivity(mainIntent);
//                        }
//                    }
//                });
//        alertDialog.show();
//    }

}
