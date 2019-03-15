package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

    TextView plusCrd;
    TextView plusBnk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_and_cards2);

        idInitialization();
        setListeners();
        setValues();

    }
    void idInitialization(){
        card = findViewById(R.id.plusCard);
        plusCrd = findViewById(R.id.plusAddCardLbl);
        bank = findViewById(R.id.plusBank);
        plusBnk = findViewById(R.id.plusLinkBankAccountLbl);
        back = findViewById(R.id.bankAndCardBackBtn);
        contactIcon = findViewById(R.id.bankCardContactUsIcon);
        profileIcon = findViewById(R.id.profileIcon);
        bankCardSettingsIcon = findViewById(R.id.bankCardSettingsIcon);
        newPostIcon = findViewById(R.id.bankAndCardNewPostIcon);
        handshakeIcon = findViewById(R.id.bankCardHandshakeIcon);
    }

    void setListeners(){
        card.setOnClickListener(plusCard);
        plusCrd.setOnClickListener(plusCard);
        bank.setOnClickListener(plusBank);
        plusBnk.setOnClickListener(plusBank);
        back.setOnClickListener(backAction);
        contactIcon.setOnClickListener(contactAction);
        profileIcon.setOnClickListener(profileIconAction);
        bankCardSettingsIcon.setOnClickListener(settingsActoin);
        newPostIcon.setOnClickListener(newPostAction);
        handshakeIcon.setOnClickListener(handHsakeAction);
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
