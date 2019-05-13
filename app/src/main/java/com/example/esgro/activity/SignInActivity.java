package com.example.esgro.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.esgro.R;
import com.example.esgro.modals.User;
import com.example.esgro.resource.Config;
import com.example.esgro.resource.LocalData;
import com.example.esgro.services.UserService;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    Button back;
    Button continueBtn;
    Button getStart;

    EditText email;
    EditText password;
    UserService service = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin );

        idInitialization();
        setListeners();
        setValues();

    }

    void idInitialization(){

        back = findViewById(R.id.siginInBackBtn);
        continueBtn = findViewById(R.id.signInContinueBtn);
        getStart = findViewById(R.id.signIngetStartBtn);
        email = findViewById(R.id.signInEmailTxt);
        password= findViewById(R.id.signInPasswordTxt);
        service = Config.getInstance().create(UserService.class);

    }

    void setListeners(){
        back.setOnClickListener(backAction);
        continueBtn.setOnClickListener(continueBtnAction);
        getStart.setOnClickListener(getStartBtnAction);
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

    View.OnClickListener backAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(SignInActivity.this,LaunchedActivity.class);
            SignInActivity.this.startActivity(mainIntent);
        }
    };

    View.OnClickListener continueBtnAction = new View.OnClickListener() {
        public void onClick(View v) {

            String e = email.getText().toString();
            String p = password.getText().toString();

            Call<JsonObject> userCall = service.login(new User(e, p));

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

                        vewAlert("Successfully","Press ok to continue",SignInActivity.this);

                    }else{
                        System.out.println(status);
                        vewAlert("Warnings","Failed to login. Try again",SignInActivity.this);

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    System.out.println("Error "+t.getMessage());
                }
            });

        }
    };

    View.OnClickListener getStartBtnAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(SignInActivity.this,SignUpActivity.class);
            SignInActivity.this.startActivity(mainIntent);
        }
    };
    public void vewAlert(final String title, String message, Context context){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // re-direct next form
                        if (title.equals("Successfully")){
                            Intent mainIntent = new Intent(SignInActivity.this, CompleteProfileActivity.class);
                            SignInActivity.this.startActivity(mainIntent);
                        }
                    }
                });
        alertDialog.show();
    }

}
