package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.esgro.R;

public class BankAndCards2Activity extends AppCompatActivity {

    Button card;
    Button bank;
    Button back;
    ImageView contactIcon;
    ImageView profileIcon;
    ImageView handshakeIcon;
    ImageView newPostIcon;
    ImageView bankCardSettingsIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_and_cards2);
        card = findViewById(R.id.plusCard);
        card.setOnClickListener(plusCard);

        bank = findViewById(R.id.plusBank);
        bank.setOnClickListener(plusBank);

        back = findViewById(R.id.bankAndCardBackBtn);
        back.setOnClickListener(backAction);

        contactIcon = findViewById(R.id.bankCardContactUsIcon);
        contactIcon.setOnClickListener(contactAction);

        profileIcon = findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(profileIconAction);

        bankCardSettingsIcon = findViewById(R.id.bankCardSettingsIcon);
        bankCardSettingsIcon.setOnClickListener(settingsActoin);

        newPostIcon = findViewById(R.id.bankAndCardNewPostIcon);
        newPostIcon.setOnClickListener(newPostAction);

        handshakeIcon = findViewById(R.id.bankCardHandshakeIcon);
        handshakeIcon.setOnClickListener(handHsakeAction);

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
    View.OnClickListener plusCard = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(BankAndCards2Activity.this, AddCardActivity.class);
            BankAndCards2Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener plusBank = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(BankAndCards2Activity.this, BankListActivity.class);
            BankAndCards2Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener backAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(BankAndCards2Activity.this, HomePageActivity.class);
            BankAndCards2Activity.this.startActivity(mainIntent);
        }
    };



    View.OnClickListener contactAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(BankAndCards2Activity.this, ContactUsActivity.class);
            BankAndCards2Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener profileIconAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(BankAndCards2Activity.this, ProfileActivity.class);
            BankAndCards2Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener settingsActoin = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(BankAndCards2Activity.this, HomePageActivity.class);
            BankAndCards2Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener newPostAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(BankAndCards2Activity.this,RequestActivity.class);
            BankAndCards2Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener handHsakeAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(BankAndCards2Activity.this,DisputeNoHistoryActivity.class);
            BankAndCards2Activity.this.startActivity(mainIntent);
        }
    };
}
