package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.esgro.R;

public class ExportActivity extends AppCompatActivity {
    Button back;
    ImageView contactIcon;
    ImageView profileIcon;
    ImageView handshakeIcon;
    ImageView newPostIcon;
    ImageView settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        back = findViewById(R.id.exportBackBtn2);
        back.setOnClickListener(backAct);

        contactIcon = findViewById(R.id.exportcontactIcon);
        contactIcon.setOnClickListener(contactUs);

        profileIcon = findViewById(R.id.exportProfileIcon);
        profileIcon.setOnClickListener(profile);

        handshakeIcon = findViewById(R.id.exportHandshakeIcon);
        handshakeIcon.setOnClickListener(handshake);

        newPostIcon = findViewById(R.id.exportNewPostIcon);
        newPostIcon.setOnClickListener(newAction);

        settings = findViewById(R.id.exportSettingsSettingsIcon);
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
}
