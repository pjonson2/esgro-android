package com.upventrix.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.upventrix.esgro.R;

public class CheckPasswordActivity extends AppCompatActivity {

    Button back;
    Button update;

    EditText currentPassword;
    EditText newPassword;
    EditText confirmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_password);

        idInitialization();
        setListeners();
        setValues();

    }
    void idInitialization(){
        back = findViewById(R.id.checkPasswordBackBtn);
        update = findViewById(R.id.updatePasswordBtn);
        currentPassword = findViewById(R.id.currentPasswordTxt);
        newPassword = findViewById(R.id.newPasswordTxt);
        confirmpassword = findViewById(R.id.confirmPasswordTxt);
    }

    void setListeners(){
        back.setOnClickListener(backAction);
        update.setOnClickListener(updatePswrdAction);
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
            Intent mainIntent = new Intent(CheckPasswordActivity.this, PreferencesActivity.class);
            CheckPasswordActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener updatePswrdAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(CheckPasswordActivity.this, HomePageActivity.class);
            CheckPasswordActivity.this.startActivity(mainIntent);
        }
    };
}
