package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.esgro.R;

public class PreferencesActivity extends AppCompatActivity {

    Button back;

    TextView changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        idInitialization();
        setListeners();
        setValues();

    }

    void idInitialization(){
        back = findViewById(R.id.preferenceBackBtn);
        changePassword = findViewById(R.id.changePasswordBtn);
    }

    void setListeners(){
        back.setOnClickListener(backAction);
        changePassword.setOnClickListener(changePasswordAction);
    }

    void setValues(){

    }

    @Override
    public void onWindowFocusChanged ( boolean hasFocus){
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
                Intent mainIntent = new Intent(PreferencesActivity.this, HomePageActivity.class);
                PreferencesActivity.this.startActivity(mainIntent);
            }
        };
        View.OnClickListener changePasswordAction = new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(PreferencesActivity.this, CheckPasswordActivity.class);
                PreferencesActivity.this.startActivity(mainIntent);
            }
        };
}

