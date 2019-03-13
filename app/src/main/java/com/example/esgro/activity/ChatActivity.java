package com.example.esgro.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.esgro.R;

public class ChatActivity extends AppCompatActivity {

    Button back;
    RadioButton disputeRadioBtn;
    Dialog dialog;
    Button close;

    ImageView handshake;
    ImageView contactUs;
    ImageView newPost;
    ImageView profileIcon;
    ImageView settingsIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        back = findViewById(R.id.chatBackBtn);
        back.setOnClickListener(backAction);

        disputeRadioBtn = findViewById(R.id.disputeRadioBtn);
        disputeRadioBtn.setOnClickListener(radioAction);

        dialog = new Dialog(this);


        handshake = findViewById(R.id.chatHandshakeIcon);
        handshake.setOnClickListener(handHsakeAction);

        newPost = findViewById(R.id.chatbankAndCardNewPostIcon);
        newPost.setOnClickListener(newPostAction);

        profileIcon = findViewById(R.id.chatprofileIcon);
        profileIcon.setOnClickListener(profileIconAction);

        settingsIcon = findViewById(R.id.chatSettingsIcon);
        settingsIcon.setOnClickListener(settingsActoin);

        contactUs = findViewById(R.id.chatContactUsIcon);
        contactUs.setOnClickListener(contactAction);


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
    View.OnClickListener backAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ChatActivity.this, DisputeDetails_01_Activity.class);
            ChatActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener radioAction = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.setContentView(R.layout.activity_warning_alert);
            dialog.show();
            Window window = dialog.getWindow();

            close = window.findViewById(R.id.warnigCloseBtn);
            close.setOnClickListener(hideUI);

            disputeRadioBtn.setSelected(true);
            disputeRadioBtn.setBackgroundResource(R.drawable.light_red_button);
            disputeRadioBtn.setTextColor(Color.WHITE);
        }
    };
    View.OnClickListener hideUI = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.dismiss();
        }
    };

    View.OnClickListener contactAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ChatActivity.this, ContactUsActivity.class);
            ChatActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener profileIconAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ChatActivity.this, ProfileActivity.class);
            ChatActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener settingsActoin = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ChatActivity.this, HomePageActivity.class);
            ChatActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener newPostAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ChatActivity.this,RequestActivity.class);
            ChatActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener handHsakeAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ChatActivity.this,DisputeNoHistoryActivity.class);
            ChatActivity.this.startActivity(mainIntent);
        }
    };
}
