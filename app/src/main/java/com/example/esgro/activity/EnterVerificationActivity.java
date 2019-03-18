package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.esgro.R;

public class EnterVerificationActivity extends AppCompatActivity {

    Button nextBtn;
    Button back;

    EditText n1TXt;
    EditText n2Txt;
    EditText n3Txt;
    EditText n4Txt;

    TextView timerView;

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
        n1TXt = findViewById(R.id.enterNumber1Txt);
        n2Txt = findViewById(R.id.enterNumber2Txt);
        n3Txt = findViewById(R.id.enterNumber3Txt);
        n4Txt = findViewById(R.id.enterNumber4Txt);
        timerView = findViewById(R.id.enterTimerLbl);
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