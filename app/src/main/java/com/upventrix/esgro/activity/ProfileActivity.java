package com.upventrix.esgro.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.upventrix.esgro.R;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.LocalData;
import com.upventrix.esgro.services.UserService;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity  extends FooterActivity {

    Button back;
    Button transfer;

    ImageView edit;
    ImageView newPost;
    ImageView handshake;
    ImageView contact;
    ImageView settings;
    ImageView profile;

    TextView firstName;
    TextView lastName;
    TextView userName;
    TextView email;
    SimpleDraweeView simpleDraweeView;

    Dialog dialog;
    String mobileNumber = "";
    String imageUrl = "";
    UserService service;
    Drawable drawable = null;
    ProgressBar progressBar;

      int userid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        dialog = new Dialog(this);

        idInitialization();
        setListeners();
        setValues();

    }

    void idInitialization(){
        edit = findViewById(R.id.editIcon);
        newPost = findViewById(R.id.profileNewPostIcon);
        handshake = findViewById(R.id.profileHandshakeIcon);
        contact = findViewById(R.id.profileContactIcon);
        settings = findViewById(R.id.profileSettingsIcon);
        profile = findViewById(R.id.profileProfileIcon);

        firstName = findViewById(R.id.profileFirstNameTxt);
        lastName = findViewById(R.id.profileLastNameTxt);
        userName = findViewById(R.id.profileUserNameTxt);
        email = findViewById(R.id.profileEmailTxt);
        transfer = findViewById(R.id.transferBtn);
        simpleDraweeView = findViewById(R.id.userImg);

        progressBar = findViewById(R.id.canceledPageProgressBAr2);
        service = Config.getInstance().create(UserService.class);
    }

    void setListeners(){
        edit.setOnClickListener(editAction);
        newPost.setOnClickListener(newAction);
        handshake.setOnClickListener(handshakeAction);
        contact.setOnClickListener(contactUs);
        settings.setOnClickListener(settingsAction);
        profile.setOnClickListener(profileActoin);
        transfer.setOnClickListener(transferAction);
    }

    void setValues(){
         final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userData = new LocalData().getlocalData(sharedPref, "userdata");
        try {
            JSONObject jsonObj = new JSONObject(userData);
            userid = jsonObj.getInt("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<JsonObject> userCall = service.details(""+userid);
        userCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                firstName.setText(response.body().get("firstname").getAsString());
                lastName.setText(response.body().get("lastname").getAsString());
                userName.setText(response.body().get("username").getAsString());
                email.setText(response.body().get("email").getAsString());


                mobileNumber = response.body().get("mobile").getAsString();
                try {
                    imageUrl = response.body().get("profileImgUrl").getAsString();

                    try {
                        Uri imageUri = Uri.parse(response.body().get("profileImgUrl").getAsString());
                        simpleDraweeView.setController(
                                Fresco.newDraweeControllerBuilder()
                                        .setOldController(simpleDraweeView.getController())
                                        .setUri(imageUri)
                                        .setTapToRetryEnabled(true)
                                        .build());
                        progressBar.setVisibility(View.GONE);
                    }catch(Exception e){
                        progressBar.setVisibility(View.GONE);
                    }
                    progressBar.setVisibility(View.GONE);
                }catch (UnsupportedOperationException e){
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("Error "+t.getMessage());
                progressBar.setVisibility(View.GONE);
             }
        });
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
    View.OnClickListener editAction = new View.OnClickListener() {
        public void onClick(View v) {
            progressBar.setVisibility(View.VISIBLE);
            Intent mainIntent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);

            String fName = firstName.getText().toString();
            String lName = lastName.getText().toString();
            String uN = userName.getText().toString();
            String mail = email.getText().toString();
            String mobile = mobileNumber;

            mainIntent.putExtra("firstName",fName);
            mainIntent.putExtra("lastName",lName);
            mainIntent.putExtra("contact",mobile);
            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String userData = new LocalData().getlocalData(sharedPref, "userdata");
            try {
                JSONObject jsonObj = new JSONObject(userData);
                userid = jsonObj.getInt("user_id");
             } catch (JSONException e) {
                e.printStackTrace();
            }

             mainIntent.putExtra("user_id",userid);
             mainIntent.putExtra("userName",uN);
             mainIntent.putExtra("email",mail);
             mainIntent.putExtra("image", imageUrl);
             ProfileActivity.this.startActivity(mainIntent);
            progressBar.setVisibility(View.GONE);
        }
    };
    View.OnClickListener contactUs = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ProfileActivity.this,ContactUsActivity.class);
            ProfileActivity.this.startActivity(mainIntent);
        }
    };

    View.OnClickListener profileActoin = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ProfileActivity.this,ProfileActivity.class);
            ProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener settingsAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ProfileActivity.this,HomePageActivity.class);
            ProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener handshakeAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ProfileActivity.this,DisputeNoHistoryActivity.class);
            ProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener newAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ProfileActivity.this,RequestActivity.class);
            ProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener transferAction = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.setContentView(R.layout.activity_transfer_alert);
            dialog.show();
            Window window = dialog.getWindow();


            ConstraintLayout bankLayout;
            ConstraintLayout cardLayout;

            TextView bankLayoutLbl;
            TextView cardLayoutLbl;

            bankLayout = window.findViewById(R.id.bankAccountView);
            cardLayout = window.findViewById(R.id.bankCardView);

            bankLayout.setOnClickListener(bankLayoutAction);
            cardLayout.setOnClickListener(cardLayoutAction);


            bankLayoutLbl = window.findViewById(R.id.bankAccountLbl);
            cardLayoutLbl = window.findViewById(R.id.cardLbl);

            bankLayoutLbl.setOnClickListener(bankLayoutAction);
            cardLayoutLbl.setOnClickListener(cardLayoutAction);

        }

            View.OnClickListener cardLayoutAction = new View.OnClickListener() {
                public void onClick(View v) {
                    Intent mainIntent = new Intent(ProfileActivity.this, TransferToCardActivity.class);
                    ProfileActivity.this.startActivity(mainIntent);
                }
            };
            View.OnClickListener bankLayoutAction = new View.OnClickListener() {
                public void onClick(View v) {
                    Intent mainIntent = new Intent(ProfileActivity.this, TransferToBankActivity.class);
                    ProfileActivity.this.startActivity(mainIntent);
                }
            };

    };
}
