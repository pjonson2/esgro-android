package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.esgro.R;

public class EnterVerificationActivity extends AppCompatActivity {

    Button nextBtn;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_verification);

        idInitialization();
        setListeners();
        setValues();

    }

    void idInitialization(){
        nextBtn = findViewById(R.id.enterVerificNxtBtn2);
        back = findViewById(R.id.enterVerificBackBtn);
    }

    void setListeners(){
        nextBtn.setOnClickListener(nextActoin);
        back.setOnClickListener(backAction);
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

    View.OnClickListener nextActoin = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(EnterVerificationActivity.this,CompleteProfileActivity.class);
            EnterVerificationActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener backAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(EnterVerificationActivity.this,MobileVerificationActivity.class);
            EnterVerificationActivity.this.startActivity(mainIntent);
        }
    };
}