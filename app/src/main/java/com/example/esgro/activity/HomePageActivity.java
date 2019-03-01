package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.esgro.R;

public class HomePageActivity extends AppCompatActivity {

    Button bankCard;
    Button about;
    Button preferences;
    Button signOut;
    Button export;
    Button cancelAndCompletedBtn;
    Button disputesBtn;
    ImageView contactIcon;
    ImageView profileIcon;
    ImageView handshakeIcon;
    ImageView newPostIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        changeBehaviours();


    }
    public void changeBehaviours(){
        bankCard = findViewById(R.id.bankAndCard);
        bankCard.setOnClickListener(bankCardAction);

        about = findViewById(R.id.aboutBtn);
        about.setOnClickListener(aboutAction);

        preferences = findViewById(R.id.preferenceaBtn);
        preferences.setOnClickListener(preferenceAction);

        signOut = findViewById(R.id.signOutBtn);
        signOut.setOnClickListener(signOutAction);

        export = findViewById(R.id.exportReqBtn);
        export.setOnClickListener(exportAction);

        cancelAndCompletedBtn = findViewById(R.id.cancelAndCompletedBtn);
        cancelAndCompletedBtn.setOnClickListener(cancelAndCompletedAction);

        disputesBtn = findViewById(R.id.disputesBtn);
        disputesBtn.setOnClickListener(disputesAction);

        contactIcon = findViewById(R.id.contactIcon);
        contactIcon.setOnClickListener(contactUs);

        profileIcon = findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(profile);

        handshakeIcon = findViewById(R.id.handshakeIcon);
        handshakeIcon.setOnClickListener(handshake);

        newPostIcon = findViewById(R.id.newPostIcon);
        newPostIcon.setOnClickListener(newAction);

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

    //  ----------------------- main view button actions  -------------------------

    View.OnClickListener bankCardAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(HomePageActivity.this,BankAndCards2Activity.class);
            HomePageActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener aboutAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(HomePageActivity.this,AboutActivity.class);
            HomePageActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener preferenceAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(HomePageActivity.this,PreferencesActivity.class);
            HomePageActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener signOutAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(HomePageActivity.this,LaunchedActivity.class);
            HomePageActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener exportAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(HomePageActivity.this,ExportActivity.class);
            HomePageActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener cancelAndCompletedAction = new View.OnClickListener() {
        public void onClick(View v) {
        }
    };
    View.OnClickListener disputesAction = new View.OnClickListener() {
        public void onClick(View v) {
        }
    };


    //  ----------------------- footer icon actions  -------------------------

    View.OnClickListener contactUs = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(HomePageActivity.this,ContactUsActivity.class);
            HomePageActivity.this.startActivity(mainIntent);
        }
    };

    View.OnClickListener profile = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(HomePageActivity.this,ProfileActivity.class);
            HomePageActivity.this.startActivity(mainIntent);
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

