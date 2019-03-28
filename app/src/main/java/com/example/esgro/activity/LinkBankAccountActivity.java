package com.example.esgro.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.esgro.R;

public class LinkBankAccountActivity extends AppCompatActivity {

    TextView textView;

    ImageView linkedBankImg;

    Button save;
    Button back;

    Bitmap bitmap;

    EditText linkBankCardNumber;
    EditText linkBankAccountNumber;

    String identifier="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_bank);


        idInitialization();
        setListeners();
        setValues();

    }

    void idInitialization(){
        back = findViewById(R.id.linkBankAccountBackBtnn);
        textView = findViewById(R.id.linkedBankNameTxt);
        linkedBankImg = findViewById(R.id.linkedBankImg);
        save = findViewById(R.id.saveAccBtn);
        linkBankCardNumber = findViewById(R.id.linkBankCardNumberTxt);
        linkBankAccountNumber = findViewById(R.id.linkBankAccountNumber);


    }

    void setListeners(){
        back.setOnClickListener(backAction);
        save.setOnClickListener(saveAction);
    }

    void setValues(){
        String bankDetails = getIntent().getStringExtra("bankListName");
        bitmap = getIntent().getParcelableExtra("BitmapImage");

        textView.setText(bankDetails);
        bitmap = getIntent().getParcelableExtra("bankListImage");
        linkedBankImg.setImageBitmap(bitmap);
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
            identifier = getIntent().getStringExtra("identifier");
            Intent mainIntent = new Intent(LinkBankAccountActivity.this,BankListActivity.class);
            mainIntent.putExtra("identifier", identifier);
            LinkBankAccountActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener saveAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(LinkBankAccountActivity.this,HomePageActivity.class);
            LinkBankAccountActivity.this.startActivity(mainIntent);
        }
    };
}
