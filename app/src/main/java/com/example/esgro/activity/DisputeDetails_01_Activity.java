package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.esgro.R;

public class DisputeDetails_01_Activity extends AppCompatActivity {

    Button submit;
    ImageView contactIcon;
    ImageView profileIcon;
    ImageView handshakeIcon;
    ImageView newPostIcon;
    ImageView settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disputes_details);

        contactIcon = findViewById(R.id.disputesContactIcon);
        contactIcon.setOnClickListener(contactUs);

        profileIcon = findViewById(R.id.disputesProfileIcon);
        profileIcon.setOnClickListener(profile);

        handshakeIcon = findViewById(R.id.disputesHandshakeIcon);
        handshakeIcon.setOnClickListener(handshake);

        newPostIcon = findViewById(R.id.disputesNewPostIcon);
        newPostIcon.setOnClickListener(newAction);

        settings = findViewById(R.id.disputesSettingsIcon);
        settings.setOnClickListener(home);

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
    View.OnClickListener contactUs = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_01_Activity.this,ContactUsActivity.class);
            DisputeDetails_01_Activity.this.startActivity(mainIntent);
        }
    };

    View.OnClickListener profile = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_01_Activity.this,ProfileActivity.class);
            DisputeDetails_01_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener home = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_01_Activity.this,HomePageActivity.class);
            DisputeDetails_01_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener handshake = new View.OnClickListener() {
        public void onClick(View v) {

        }
    };
    View.OnClickListener newAction = new View.OnClickListener() {
        public void onClick(View v) {

        }
    };
}
