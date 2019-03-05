package com.example.esgro.activity;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.esgro.R;

public class CompleteProfileActivity  extends AppCompatActivity {
    Button back;
    Button continueBtn;
    Button plusAddCard;
    Button plusLinkBank;
    ImageView selectImg;

    TextView plusLinkbankAccount;
    TextView plusCrd;

    Intent intent;

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
        plusCrd = findViewById(R.id.plusAddCrdLbl);
        plusCrd.setOnClickListener(plusAddBank);

        plusLinkBank = findViewById(R.id.plusLinkBankBtn);
        plusLinkBank.setOnClickListener(plusLinkBanks);

        plusLinkbankAccount = findViewById(R.id.plusLinkBankLbl);
        plusLinkbankAccount.setOnClickListener(plusLinkBanks);

        selectImg = findViewById(R.id.selectImg);
        selectImg.setOnClickListener(selectFile);
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
            Intent mainIntent = new Intent(CompleteProfileActivity.this,BankListActivity.class);
            CompleteProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener selectFile = new View.OnClickListener() {
        public void onClick(View v) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent,10);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
//                    String path = data.getData().getPath();
//                    Icon icon= new Icon(p);
//                    selectImg.setImageIcon();

//                    System.out.println("path is  " + path);
                }
                break;
        }
    }
}
