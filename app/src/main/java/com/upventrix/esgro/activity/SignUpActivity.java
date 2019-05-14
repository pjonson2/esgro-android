package com.upventrix.esgro.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.User;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.LocalData;
import com.upventrix.esgro.resource.Validations;
import com.upventrix.esgro.services.UserService;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    Button signUpBackBtn;
    Button continueBtn;

    EditText firstName;
    EditText lastName;
    EditText userName;
    EditText email;
    EditText password;
    EditText passwordReType;
    UserService service = null;
    String key = null;

    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        idInitialization();
        setListeners();
        setValues();

    }

    @SuppressLint("ResourceType")
    void idInitialization(){
        signUpBackBtn = findViewById(R.id.signUpBackBtn);
        continueBtn = findViewById(R.id.signUpContinueBtn);
        firstName = findViewById(R.id.signUpFirstNameTxt);
        lastName = findViewById(R.id.signUpLastNameTxt);
        userName = findViewById(R.id.signUpUserNameTxt);
        email = findViewById(R.id.signUpEmailTxt);
        password = findViewById(R.id.signUpPswrdTxt);
        passwordReType = findViewById(R.id.signUpPswrdReTypeTxt);
        service = Config.getInstance().create(UserService.class);
        key = getResources().getString(R.string.userdata);

    }

    void setListeners(){
        signUpBackBtn.setOnClickListener(signUpBack);
        continueBtn.setOnClickListener(continueBtnAction);
    }

    void setValues(){

    }

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
    View.OnClickListener signUpBack = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(SignUpActivity.this,LaunchedActivity.class);
            SignUpActivity.this.startActivity(mainIntent);
        }
    };

    View.OnClickListener continueBtnAction = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void onClick(View v) {
            ShapeDrawable shape = new ShapeDrawable(new RectShape());
            boolean emailValid = new Validations().isEmailValid(email.getText().toString());
            if (!emailValid){
                vewAlert("Warnings","Invalid email address",SignUpActivity.this);
                return;
            }
            if(!password.getText().toString().equals(passwordReType.getText().toString())){

                shape.getPaint().setColor(Color.RED);
                shape.getPaint().setStyle(Paint.Style.STROKE);
                shape.getPaint().setStrokeWidth(1);
                password.setBackground(shape);
                passwordReType.setBackground(shape);

                vewAlert("Warnings","Password doesn't matched",SignUpActivity.this);
                return;
            }

            // calling user api

            Call<JsonObject> userCall = service.saveUser(
                    new User(
                    firstName.getText().toString(),
                    lastName.getText().toString(),
                    userName.getText().toString(),
                    email.getText().toString(),
                    password.getText().toString()
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

                        JsonObject userData = response.body().getAsJsonObject("userdata");
                        // set local user data
                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        new LocalData().setLocalData(sharedPref,userData);

                        // re-direct next form
                        vewAlert("Successfully","Your details successfully saved",SignUpActivity.this);

                    }else{
                        vewAlert("Warnings","Your details saving failed",SignUpActivity.this);
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    System.out.println("Error "+t.getMessage());
                }
            });

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
                            Intent mainIntent = new Intent(context, MobileVerificationActivity.class);
                            context.startActivity(mainIntent);
                        }
                    }
                });
        alertDialog.show();
    }


}