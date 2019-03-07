package com.example.esgro.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.esgro.R;

public class DisputeDetails_No_History_Activity extends AppCompatActivity {

    Button back;
    ImageView contactIcon;
    ImageView profileIcon;
    ImageView handshakeIcon;
    ImageView newPostIcon;
    ImageView settings;
    Dialog dialog;
    ImageView okIconImge;
    ImageView noDisputeCancelBtn;
    private final int SPLASH_DISPLAY_LENGTH = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disputes_details_no_history);

        contactIcon = findViewById(R.id.noDisputesContactIcon);
        contactIcon.setOnClickListener(contactUs);

        profileIcon = findViewById(R.id.noDisputesProfileIcon);
        profileIcon.setOnClickListener(profile);

        handshakeIcon = findViewById(R.id.noDisputesHandshakeIcon);
        handshakeIcon.setOnClickListener(handshake);

        newPostIcon = findViewById(R.id.noDisputesNewPostIcon);
        newPostIcon.setOnClickListener(newAction);

        settings = findViewById(R.id.noDisputesSettingsIcon);
        settings.setOnClickListener(home);

        back = findViewById(R.id.noDisputeDetailsBack);
        back.setOnClickListener(backToTimeLine);

        okIconImge = findViewById(R.id.okIconImge);
        okIconImge.setOnClickListener(submitAction);

        noDisputeCancelBtn = findViewById(R.id.noDisputeCancelBtn);
        noDisputeCancelBtn.setOnClickListener(cancelAction);

        dialog = new Dialog(this);

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
    View.OnClickListener submitAction = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.setContentView(R.layout.activity_proessing_alert);

            dialog.show();
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    };
    View.OnClickListener cancelAction = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.setContentView(R.layout.activity_dispute_cancel_alert);
            dialog.show();
            Window window = dialog.getWindow();
            Button button;

            button = window.findViewById(R.id.goBackBtn);
            button.setOnClickListener(hideUI);

        }

        View.OnClickListener hideUI = new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        };

    };
    View.OnClickListener contactUs = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_No_History_Activity.this,ContactUsActivity.class);
            DisputeDetails_No_History_Activity.this.startActivity(mainIntent);
        }
    };

    View.OnClickListener profile = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_No_History_Activity.this,ProfileActivity.class);
            DisputeDetails_No_History_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener home = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_No_History_Activity.this,HomePageActivity.class);
            DisputeDetails_No_History_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener backToTimeLine = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_No_History_Activity.this,DisputeNoHistoryActivity.class);
            DisputeDetails_No_History_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener handshake = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_No_History_Activity.this,DisputeNoHistoryActivity.class);
            DisputeDetails_No_History_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener newAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_No_History_Activity.this,RequestActivity.class);
            DisputeDetails_No_History_Activity.this.startActivity(mainIntent);
        }
    };
}