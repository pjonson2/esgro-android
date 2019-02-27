package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.esgro.R;

public class SignUpActivity extends AppCompatActivity {
    Button signUpBackBtn;
    Button continueBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signUpBackBtn = findViewById(R.id.signUpBackBtn);
        signUpBackBtn.setOnClickListener(signUpBack);

        continueBtn = findViewById(R.id.signUpContinueBtn);
        continueBtn.setOnClickListener(continueBtnAction);

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
        public void onClick(View v) {
            Intent mainIntent = new Intent(SignUpActivity.this,MobileVerificationActivity.class);
            SignUpActivity.this.startActivity(mainIntent);
        }
    };
}