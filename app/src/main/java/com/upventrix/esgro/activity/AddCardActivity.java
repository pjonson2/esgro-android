package com.upventrix.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.upventrix.esgro.R;

public class AddCardActivity extends AppCompatActivity {

    Button back;
    Button addcrd;

    EditText cardNumber;
    EditText expDate;
    EditText cvv;

    String identifier = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_credit_debit);

        identifier = getIntent().getStringExtra("identifier");

        idInitialization();
        setListeners();
        setValues();

    }

    void idInitialization(){
        back = findViewById(R.id.addCardBackBtn);
        addcrd = findViewById(R.id.addCrdBtn);
        cardNumber = findViewById(R.id.CardCardNumberTxt);
        expDate = findViewById(R.id.CardExpDateTxt);
        cvv = findViewById(R.id.CardCvvTxt);
        cardNumber.addTextChangedListener(checkNumbers);

    }

    void setListeners(){
        back.setOnClickListener(addCardBAck);
        addcrd.setOnClickListener(addCardAction);
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
    View.OnClickListener addCardBAck = new View.OnClickListener() {
        public void onClick(View v) {

            if (identifier.equals("TransferToCardActivity")){
                Intent mainIntent = new Intent(AddCardActivity.this, TransferToCardActivity.class);
                AddCardActivity.this.startActivity(mainIntent);
            }

            if (identifier.equals("BankAndCards2Activity")){
                Intent mainIntent = new Intent(AddCardActivity.this, BankAndCards2Activity.class);
                AddCardActivity.this.startActivity(mainIntent);
            }

            if (identifier.equals("CompleteProfileActivity")){
                Intent mainIntent = new Intent(AddCardActivity.this, CompleteProfileActivity.class);
                AddCardActivity.this.startActivity(mainIntent);
            }


        }
    };
    View.OnClickListener addCardAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(AddCardActivity.this, HomePageActivity.class);
            AddCardActivity.this.startActivity(mainIntent);
        }
    };

    TextWatcher checkNumbers = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (cardNumber.getText().toString().length() == 4){

            }
            if (cardNumber.getText().toString().length() == 9){

            }

        }

        @Override
        public void afterTextChanged(Editable s) {

            System.out.println("afterTextChanged");

        }
    };

}
