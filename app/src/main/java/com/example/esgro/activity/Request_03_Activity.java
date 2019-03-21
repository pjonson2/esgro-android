package com.example.esgro.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.esgro.R;

public class Request_03_Activity extends AppCompatActivity {

    Button back;
    Button continues;

    Dialog dialog;

    private final int SPLASH_DISPLAY_LENGTH = 4000;
    private final int SPLASH_DISPLAY_LENGTH_1 = 2000;

    EditText userName;
    EditText days;
    EditText amount;
    EditText reserve;
    EditText description;

    Bundle extras;

    String charging_amount;
    String holding_days;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_request_3);
        extras = getIntent().getExtras();

        idInitialization();
        setListeners();
        setValues();

        dialog = new Dialog(this);

    }

    void idInitialization(){
        back = findViewById(R.id.request3Back);
        continues = findViewById(R.id.request3SendBtn);

        userName = findViewById(R.id.requestToUserNameTxt);
        days = findViewById(R.id.requestDaysTxt);
        amount = findViewById(R.id.requestAmountTxt);
        reserve = findViewById(R.id.requestReserveTxt);
        description = findViewById(R.id.requestDescriptionTxt);
    }

    void setListeners(){
        back.setOnClickListener(requestBack);
        continues.setOnClickListener(requestContinue);
        userName.setText(extras.getString("request_user"));
    }

    void setValues(){
        holding_days = extras.getString("holding_days");
        charging_amount = extras.getString("charging_amount");
        amount.setText("$"+charging_amount);
        days.setText(holding_days);

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

            String days = extras.getString("holding_days");
            String prices = extras.getString("charging_amount");

            Intent mainIntent = new Intent(Request_03_Activity.this,Request_02_Activity.class);
            mainIntent.putExtra("holding_days",days);
            mainIntent.putExtra("charging_amount",prices);
            mainIntent.putExtra("request_user",userName.getText().toString());

            Request_03_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener requestContinue = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.setContentView(R.layout.activity_proessing_alert);
            dialog.setContentView(R.layout.activity_proessing_alert);

            dialog.show();
            Window window = dialog.getWindow();

            final TextView weLbl = window.findViewById(R.id.weLbl);
            final TextView esgroLbl = window.findViewById(R.id.esgroLbl);
            final TextView themLbl = window.findViewById(R.id.themLbl);
            esgroLbl.setTextColor(Color.parseColor("#929AAB"));
            weLbl.setTextColor(Color.parseColor("#7FE239"));

            weLbl.setText(charging_amount);
            esgroLbl.setText(charging_amount);
            themLbl.setText(charging_amount);

            new Handler().postDelayed(new Runnable(){


                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void run() {

                    if(Double.parseDouble(charging_amount)>0){
                        weLbl.setTextColor(Color.parseColor("#929AAB"));
                        esgroLbl.setTextColor(Color.parseColor("#7FE239"));
                    }
                }
            }, SPLASH_DISPLAY_LENGTH_1);

            dialog.show();
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    dialog.dismiss();

                    Intent mainIntent = new Intent(Request_03_Activity.this,DisputeNoHistoryActivity.class);
                    Request_03_Activity.this.startActivity(mainIntent);
                }
            }, SPLASH_DISPLAY_LENGTH);

        }
    };
}