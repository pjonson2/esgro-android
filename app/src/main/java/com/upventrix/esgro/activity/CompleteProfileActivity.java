package com.upventrix.esgro.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.upventrix.esgro.R;

public class CompleteProfileActivity  extends AppCompatActivity {

    Button back;
    Button continueBtn;
    Button plusAddCard;
    Button plusLinkBank;
    Button skip;

    ImageView selectImg;

    TextView plusLinkbankAccount;
    TextView plusCrd;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        idInitialization();
        setListeners();
        setValues();

    }

    void idInitialization(){
        back = findViewById(R.id.completeProfileBackBtn2);
        continueBtn = findViewById(R.id.completeProfileContinueBtn);
        plusAddCard = findViewById(R.id.plusCompleteAddBankBtn);
        plusCrd = findViewById(R.id.plusAddCrdLbl);
        plusLinkBank = findViewById(R.id.plusLinkBankBtn);
        plusLinkbankAccount = findViewById(R.id.plusLinkBankLbl);
        selectImg = findViewById(R.id.selectImg);
        skip = findViewById(R.id.skipNowBtn);
    }

    void setListeners(){
        back.setOnClickListener(backAction);
        continueBtn.setOnClickListener(continues);
        plusAddCard.setOnClickListener(plusAddBank);
        plusCrd.setOnClickListener(plusAddBank);
//        plusLinkBank.setOnClickListener(plusLinkBanks);
        plusLinkbankAccount.setOnClickListener(plusLinkBanks);
        selectImg.setOnClickListener(selectFile);
        skip.setOnClickListener(skipAction);
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

    View.OnClickListener backAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(CompleteProfileActivity.this,EnterVerificationActivity.class);
            CompleteProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener continues = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(CompleteProfileActivity.this,ProfileActivity.class);
            CompleteProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener plusAddBank = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(CompleteProfileActivity.this, AddCardActivity.class);
            mainIntent.putExtra("identifier","CompleteProfileActivity");
            CompleteProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener plusLinkBanks = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(CompleteProfileActivity.this,BankListActivity.class);
            mainIntent.putExtra("identifier","CompleteProfileActivity");
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
    View.OnClickListener skipAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(CompleteProfileActivity.this,DisputeNoHistoryActivity.class);
            CompleteProfileActivity.this.startActivity(mainIntent);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {

                    System.out.println("image data inside switch");
                    Uri selectedImageUri = data.getData();
//                    String path = selectedImageUri.getPath();
//
//                    System.out.println("path iss" + path );
//
//                    Bitmap thumbnail = BitmapFactory.decodeFile(path);

                    selectImg.setImageURI(selectedImageUri);

                }
                break;
        }
    }
    @Override
    public void onBackPressed() {
        System.out.println("You clicked back button");
//        if (!shouldAllowBack()) {
//            doSomething();
//        } else {
//            super.onBackPressed();
//        }
    }
}
