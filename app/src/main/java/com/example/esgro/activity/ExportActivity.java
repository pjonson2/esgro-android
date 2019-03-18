package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.esgro.R;

public class ExportActivity extends AppCompatActivity {

    ImageView contactIcon;
    ImageView profileIcon;
    ImageView handshakeIcon;
    ImageView newPostIcon;
    ImageView settings;

    CheckBox exportIncomingCheck;
    CheckBox exportOutgoingChack;
    CheckBox exportCompletedRadio;
    CheckBox exportRejectedCheck;

    RadioButton exportMonthsRadio;
    RadioButton exportAllTimeRadio;
    RadioButton exportAllRequestRadio;
    RadioButton exportAllRequestStatusRadio;

    Button exportSendBtn;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        idInitialization();
        setListeners();
        setValues();

    }

    void idInitialization(){
        back = findViewById(R.id.exportBackBtn2);
        contactIcon = findViewById(R.id.exportcontactIcon);
        profileIcon = findViewById(R.id.exportProfileIcon);
        handshakeIcon = findViewById(R.id.exportHandshakeIcon);
        newPostIcon = findViewById(R.id.exportNewPostIcon);
        settings = findViewById(R.id.exportSettingsSettingsIcon);
        exportAllRequestRadio = findViewById(R.id.exportAllRequestRadio);
        exportAllRequestStatusRadio = findViewById(R.id.exportAllRequestStatusRadio);

        exportIncomingCheck = findViewById(R.id.exportIncomingCheck);
        exportOutgoingChack = findViewById(R.id.exportOutgoingCheck);
        exportCompletedRadio = findViewById(R.id.exportCompletedCheck);
        exportRejectedCheck = findViewById(R.id.exportRejectedCheck);
        exportMonthsRadio = findViewById(R.id.exportMonthsRadio);
        exportAllTimeRadio = findViewById(R.id.exportAllTimeRadio);
        exportSendBtn = findViewById(R.id.exportSendBtn);


    }

    void setListeners(){
        back.setOnClickListener(backAct);
        contactIcon.setOnClickListener(contactUs);
        profileIcon.setOnClickListener(profile);
        handshakeIcon.setOnClickListener(handshake);
        newPostIcon.setOnClickListener(newAction);
        settings.setOnClickListener(home);
        exportSendBtn.setOnClickListener(send);
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
    View.OnClickListener backAct = new View.OnClickListener() {
        public void onClick(View v) {

            Intent mainIntent = new Intent(ExportActivity.this,HomePageActivity.class);
            ExportActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener contactUs = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ExportActivity.this,ContactUsActivity.class);
            ExportActivity.this.startActivity(mainIntent);
        }
    };

    View.OnClickListener profile = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ExportActivity.this,ProfileActivity.class);
            ExportActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener home = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ExportActivity.this,HomePageActivity.class);
            ExportActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener handshake = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ExportActivity.this,DisputeNoHistoryActivity.class);
            ExportActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener newAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ExportActivity.this,RequestActivity.class);
            ExportActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener send = new View.OnClickListener() {
        public void onClick(View v) {
//            Intent mainIntent = new Intent(ExportActivity.this,RequestActivity.class);
//            ExportActivity.this.startActivity(mainIntent);
        }
    };
}
