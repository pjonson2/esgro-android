package com.example.esgro.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.esgro.R;
import com.example.esgro.resource.LocalData;

public class MainActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idInitialization();
        setListeners();
        setValues();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                String sf = new LocalData().getlocalData(sharedPref, "userdata");
                System.out.println("USER DATA "+sf);
                System.out.println("USER DATA "+sf);
                //boolean isEmpty = sf == null || sf.trim().length() == 0;

                //System.out.println("check :"+ isEmpty);

                if (sf.length() == 4) {
                    System.out.println("user data null");
                    Intent mainIntent = new Intent(MainActivity.this, LaunchedActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                }else {
                    System.out.println("user data not null");
                    Intent mainIntent = new Intent(MainActivity.this,DisputeNoHistoryActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();

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
