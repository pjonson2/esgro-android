package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.esgro.R;

public class MobileVerificationActivity  extends AppCompatActivity {

    Button backBtn;
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);

        backBtn = findViewById(R.id.verificationBackBtn);
        backBtn.setOnClickListener(backAction);

        nextBtn = findViewById(R.id.mobileVerificNxtBtn);
        nextBtn.setOnClickListener(nxtAction);
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
            Intent mainIntent = new Intent(MobileVerificationActivity.this,SignUpActivity.class);
            MobileVerificationActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener nxtAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(MobileVerificationActivity.this,EnterVerificationActivity.class);
            MobileVerificationActivity.this.startActivity(mainIntent);
        }
    };
}
