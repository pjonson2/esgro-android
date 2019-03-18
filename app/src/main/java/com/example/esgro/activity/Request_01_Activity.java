package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.esgro.R;

public class Request_01_Activity extends AppCompatActivity {

    Button back;
    Button continues;

    EditText requestChargingAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_1);

        idInitialization();
        setListeners();
        setValues();

    }

    void idInitialization(){
        back = findViewById(R.id.requestBackBtn);
        continues = findViewById(R.id.requestContinueBtn);
        requestChargingAmount = findViewById(R.id.requestChargingAmountTxt);
    }

    void setListeners(){
        back.setOnClickListener(requestBack);
        continues.setOnClickListener(requestContinue);
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
    View.OnClickListener requestBack = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(Request_01_Activity.this,RequestActivity.class);
            Request_01_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener requestContinue = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(Request_01_Activity.this,Request_02_Activity.class);
            Request_01_Activity.this.startActivity(mainIntent);
        }
    };
}
