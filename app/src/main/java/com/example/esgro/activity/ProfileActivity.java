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

        idInitialization();
        setListeners();
        setValues();

    }

    void idInitialization(){
        edit = findViewById(R.id.editIcon);
        newPost = findViewById(R.id.profileNewPostIcon);
        handshake = findViewById(R.id.profileHandshakeIcon);
        contact = findViewById(R.id.profileContactIcon);
        settings = findViewById(R.id.profileSettingsIcon);
        profile = findViewById(R.id.profileProfileIcon);
    }

    void setListeners(){
        edit.setOnClickListener(editAction);
        newPost.setOnClickListener(newAction);
        handshake.setOnClickListener(handshakeAction);
        contact.setOnClickListener(contactUs);
        settings.setOnClickListener(settingsAction);
        profile.setOnClickListener(profileActoin);
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
