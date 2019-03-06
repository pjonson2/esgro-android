package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.esgro.R;

public class ProfileActivity  extends FooterActivity {

    Button back;
    ImageView edit;
    ImageView newPost;
    ImageView handshake;
    ImageView contact;
    ImageView settings;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        edit = findViewById(R.id.editIcon);
        edit.setOnClickListener(editAction);

        newPost = findViewById(R.id.profileNewPostIcon);
        newPost.setOnClickListener(newAction);

        handshake = findViewById(R.id.profileHandshakeIcon);
        handshake.setOnClickListener(handshakeAction);

        contact = findViewById(R.id.profileContactIcon);
        contact.setOnClickListener(contactUs);

        settings = findViewById(R.id.profileSettingsIcon);
        settings.setOnClickListener(settingsAction);

        profile = findViewById(R.id.profileProfileIcon);
        profile.setOnClickListener(profileActoin);

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
    View.OnClickListener editAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
            ProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener contactUs = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ProfileActivity.this,ContactUsActivity.class);
            ProfileActivity.this.startActivity(mainIntent);
        }
    };

    View.OnClickListener profileActoin = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ProfileActivity.this,ProfileActivity.class);
            ProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener settingsAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ProfileActivity.this,HomePageActivity.class);
            ProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener handshakeAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ProfileActivity.this,DisputeNoHistoryActivity.class);
            ProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener newAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ProfileActivity.this,RequestActivity.class);
            ProfileActivity.this.startActivity(mainIntent);
        }
    };
}
