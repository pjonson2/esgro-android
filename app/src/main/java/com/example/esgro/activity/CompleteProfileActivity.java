package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.esgro.R;

public class CompleteProfileActivity  extends AppCompatActivity {
    Button back;
    Button continueBtn;
    Button plusAddCard;
    Button plusLinkBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        back = findViewById(R.id.completeProfileBackBtn2);
        back.setOnClickListener(backAction);

        continueBtn = findViewById(R.id.completeProfileContinueBtn);
        continueBtn.setOnClickListener(continues);

        plusAddCard = findViewById(R.id.plusCompleteAddBankBtn);
        plusAddCard.setOnClickListener(plusAddBank);
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
            Intent mainIntent = new Intent(CompleteProfileActivity.this,EnterVerificationActivity.class);
            CompleteProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener continues = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(CompleteProfileActivity.this,HomePageActivity.class);
            CompleteProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener plusAddBank = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(CompleteProfileActivity.this, AddCardActivity.class);
            CompleteProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener plusLinkBanks = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(CompleteProfileActivity.this,AddCardActivity.class);
            CompleteProfileActivity.this.startActivity(mainIntent);
        }
    };
}
