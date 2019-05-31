package com.upventrix.esgro.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.UserToken;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.LocalData;
import com.upventrix.esgro.services.UserService;
import com.google.gson.JsonObject;
//import com.google.firebase.iid.;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private  UserService service;
    private static final String TAG = "MainActivity";
    int userid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);


        service = Config.getInstance().create(UserService.class);
        idInitialization();
        setListeners();
        setValues();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String temp_userdata = new LocalData().getlocalData(sharedPref, "temp_userdata")+"";

                if (temp_userdata.length()==4){
                }else{
                    Intent mainIntent = new Intent(MainActivity.this,SignUpActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                    return;
                }

                String userData = new LocalData().getlocalData(sharedPref, "userdata")+"";
                if (userData.length() == 4) {
                    System.out.println("user data null");
                    Intent mainIntent = new Intent(MainActivity.this, LaunchedActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                    return;
                }

                try {
                    JSONObject jsonObj = new JSONObject(userData);
                    userid = jsonObj.getInt("user_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String sf = new LocalData().getlocalData(sharedPref, "userdata")+"";
                System.out.println("USER DATA "+sf);

                if (sf.length() == 4) {
                    System.out.println("user data null");
                    Intent mainIntent = new Intent(MainActivity.this, LaunchedActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                }else {
                    System.out.println("user data not null");

                    Call<JsonObject> userCall = service.details(""+userid);
                    userCall.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            System.out.println("response "+response.body());

                            String number = response.body().get("mobile").toString();
                            if (number.length()==4){
                                new LocalData().setTempLocalData(sharedPref,null);
                                Intent mainIntent = new Intent(MainActivity.this,MobileVerificationActivity.class);
                                MainActivity.this.startActivity(mainIntent);
                                MainActivity.this.finish();

                            }else{
                                new LocalData().setTempLocalData(sharedPref,null);
                                Intent mainIntent = new Intent(MainActivity.this,DisputeNoHistoryActivity.class);
                                MainActivity.this.startActivity(mainIntent);
                                MainActivity.this.finish();
                            }

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            System.out.println("Error "+t.getMessage());
                        }
                    });

                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
    void idInitialization(){

    }

    void setListeners(){

    }

    void setValues(){

    }

    public static void main(String[] args) {
        System.out.println("Main Method..................");
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

}
