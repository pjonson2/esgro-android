package com.example.esgro.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.esgro.R;
import com.example.esgro.activity.alert.FeedBackAlertActivity;

public class ContactUsActivity extends AppCompatActivity {

    Button submit;
    ImageView settings;
    ImageView profileIcon;
    ImageView handshakeIcon;
    ImageView newPostIcon;

    Dialog dialog;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        submit = findViewById(R.id.submitBtn);
        submit.setOnClickListener(submitAction);

        profileIcon = findViewById(R.id.contactProfileIcon);
        profileIcon.setOnClickListener(profile);

        handshakeIcon = findViewById(R.id.contactHandshakeIcon);
        handshakeIcon.setOnClickListener(handshake);

        newPostIcon = findViewById(R.id.contactNewPostIcon);
        newPostIcon.setOnClickListener(newAction);

        settings = findViewById(R.id.contactSettingsIcon);
        settings.setOnClickListener(settingsAction);
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
            dialog.setContentView(R.layout.activity_feedback_alert);

            dialog.show();
            Window window = dialog.getWindow();

            done = window.findViewById(R.id.doneBtn);
            done.setOnClickListener(hideUI);
        }
    };
    View.OnClickListener hideUI = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.dismiss();
        }
    };

    View.OnClickListener profile = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ContactUsActivity.this,ProfileActivity.class);
            ContactUsActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener settingsAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ContactUsActivity.this,HomePageActivity.class);
            ContactUsActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener handshake = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ContactUsActivity.this,DisputeNoHistoryActivity.class);
            ContactUsActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener newAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ContactUsActivity.this,RequestActivity.class);
            ContactUsActivity.this.startActivity(mainIntent);
        }
    };
}
