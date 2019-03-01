package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.esgro.R;

public class UpdateProfileActivity   extends AppCompatActivity {

    Button back;
    Button updateCancelBtn;
    Button saveBtn;

    ImageView newPost;
    ImageView handshake;
    ImageView contact;
    ImageView settings;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        updateCancelBtn = findViewById(R.id.updateCancelBtn);
        updateCancelBtn.setOnClickListener(cancelAction);

        saveBtn = findViewById(R.id.updateSaveBtn);
        saveBtn.setOnClickListener(saveAction);

        newPost = findViewById(R.id.updateProfileNewPostIcon);
        newPost.setOnClickListener(newAction);

        handshake = findViewById(R.id.updateProfileHandshakeIcon);
        handshake.setOnClickListener(handshakeAction);

        contact = findViewById(R.id.updateProfileContactIcon);
        contact.setOnClickListener(contactUs);

        settings = findViewById(R.id.updateprofileSettingsIcon);
        settings.setOnClickListener(settingsAction);

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
    View.OnClickListener cancelAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
            UpdateProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener saveAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
            UpdateProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener contactUs = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(UpdateProfileActivity.this,ContactUsActivity.class);
            UpdateProfileActivity.this.startActivity(mainIntent);
        }
    };

    View.OnClickListener settingsAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(UpdateProfileActivity.this,HomePageActivity.class);
            UpdateProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener handshakeAction = new View.OnClickListener() {
        public void onClick(View v) {

        }
    };
    View.OnClickListener newAction = new View.OnClickListener() {
        public void onClick(View v) {

        }
    };
}
