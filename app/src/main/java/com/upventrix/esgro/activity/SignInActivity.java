package com.upventrix.esgro.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.rafaelbarbosatec.archivimentview.AchievementView;
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.User;
import com.upventrix.esgro.modals.UserToken;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.LocalData;
import com.upventrix.esgro.services.NotificationService;
import com.upventrix.esgro.services.UserService;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

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

    ProgressBar progressBar;

    ConstraintLayout constraintLayout;
    ConstraintLayout constraintLayout2;
    private String deviceName;
    private NotificationService notificationService;
    AchievementView achievementView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin );

        idInitialization();
        setListeners();
        setValues();
        constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout2 = findViewById(R.id.cardView);

        constraintLayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });
        constraintLayout2.setOnTouchListener(new View.OnTouchListener()
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

        back = findViewById(R.id.siginInBackBtn);
        continueBtn = findViewById(R.id.signInContinueBtn);
        getStart = findViewById(R.id.signIngetStartBtn);
        email = findViewById(R.id.signInEmailTxt);
        password= findViewById(R.id.signInPasswordTxt);
        progressBar = findViewById(R.id.progressBar4);
        service = Config.getInstance().create(UserService.class);
        achievementView = findViewById(R.id.achievementView);
        notificationService = Config.getInstance().create(NotificationService.class);

    }

    void setListeners(){
        back.setOnClickListener(backAction);
        continueBtn.setOnClickListener(continueBtnAction);
        getStart.setOnClickListener(getStartBtnAction);
        progressBar.setVisibility(View.GONE);
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
            progressBar.setVisibility(View.VISIBLE);
            String e = email.getText().toString();
            String p = password.getText().toString();

            Call<JsonObject> userCall = service.login(new User(e, p));

            userCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    String status = "";
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
                         progressBar.setVisibility(View.GONE);
                        setToken(SignInActivity.this);
                        vewAlert("Successfully","Press ok to continue",SignInActivity.this);

                    }else{
                        progressBar.setVisibility(View.GONE);
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
    public void vewAlert(final String title, String message, final Context context){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String userData = new LocalData().getlocalData(sharedPref, "userdata")+"";
        int userid = 0;
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
                if (title.equals("Successfully")){
                String number = response.body().get("mobile").toString();
                 if (number.length()==4){
                    Intent mainIntent = new Intent(context,MobileVerificationActivity.class);
                    context.startActivity(mainIntent);
                    SignInActivity.this.finish();

                }else{
                    new LocalData().setTempLocalData(sharedPref,null);
                    String profileImgUrl = response.body().get("profileImgUrl").toString();
                    System.out.println("profileImgUrl   "+profileImgUrl);
                    if  (profileImgUrl.length() == 4){
                        Intent mainIntent = new Intent(SignInActivity.this, CompleteProfileActivity.class);
                        SignInActivity.this.startActivity(mainIntent);
                    }else{
                         Intent mainIntent = new Intent(SignInActivity.this, DisputeNoHistoryActivity.class);
                        SignInActivity.this.startActivity(mainIntent);
                    }
                    }
                }else{
                    new ToastActivity().showFailed(achievementView,"Warnings!","Failed to login");
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("Error "+t.getMessage());
            }
        });
    }


    private void setToken( final Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String notification_token = new LocalData().getlocalData(sharedPref, "device_token")+"";
        System.out.println("notification_token (device_token)  "+notification_token);

        if(notification_token.length() == 4){
            System.out.println("notification_token  NULL ");
            return;
        }

        String userData = new LocalData().getlocalData(sharedPref, "userdata");
        int userid = 0;
        try {
            JSONObject jsonObj = new JSONObject(userData);
            userid = jsonObj.getInt("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String model = Build.MODEL;
        String brand = Build.BRAND;
        String device = Build.DEVICE;
        deviceName = brand+" "+device+" "+model;
        System.out.println("deviceName  "+deviceName);

        Call<JsonObject> jsonObjectCall = notificationService.setToken(
                new UserToken(
                        notification_token,
                        deviceName,
                        userid
                )
        );
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String status = "";
                try {
                    status = response.body().get("status").getAsString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("The Status iS "+status);
                if (status.equals("success")){
                    String device_id = response.body().get("device_id").getAsString();
                    System.out.println("Status Success! "+" device_id  "+device_id+" notification_token  "+notification_token);
//                    new LocalData().setNotificationToken(sharedPref,notification_token);
                    new LocalData().setDeviceId(sharedPref,device_id);
                   String d = new LocalData().getlocalData(sharedPref, "device_id");
                    System.out.println("device --- - - "+d);

                }else{
                    System.out.println("Status Failed!");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("ERROR!");
            }
        });
    }


}
