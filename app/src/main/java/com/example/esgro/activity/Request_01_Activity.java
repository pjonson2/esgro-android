package com.example.esgro.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.esgro.R;

public class Request_01_Activity extends AppCompatActivity {

    Button back;
    Button continues;
    Button n0;
    Button n1;
    Button n2;
    Button n3;
    Button n4;
    Button n5;
    Button n6;
    Button n7;
    Button n8;
    Button n9;
    Button n10;
    Button n11;

    EditText requestChargingAmount;

    String amount="";
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_1);
        extras = getIntent().getExtras();

        idInitialization();
        setListeners();
        setValues();

    }

    void idInitialization(){
        back = findViewById(R.id.requestBackBtn);
        continues = findViewById(R.id.requestContinueBtn);
        requestChargingAmount = findViewById(R.id.requestChargingAmountTxt);

        n1 =  findViewById(R.id.n1);
        n2 =  findViewById(R.id.n2);
        n3 =  findViewById(R.id.n3);
        n4 =  findViewById(R.id.n4);
        n5 =  findViewById(R.id.n5);
        n6 =  findViewById(R.id.n6);
        n7 =  findViewById(R.id.n7);
        n8 =  findViewById(R.id.n8);
        n9 =  findViewById(R.id.n9);
        n10 =  findViewById(R.id.n10);
        n11 =  findViewById(R.id.n11);
        n0 =  findViewById(R.id.n0);


    }

    void setListeners(){
        back.setOnClickListener(requestBack);
        continues.setOnClickListener(requestContinue);
        n1.setOnClickListener(numberPad);
        n2.setOnClickListener(numberPad);
        n3.setOnClickListener(numberPad);
        n4.setOnClickListener(numberPad);
        n5.setOnClickListener(numberPad);
        n6.setOnClickListener(numberPad);
        n7.setOnClickListener(numberPad);
        n8.setOnClickListener(numberPad);
        n9.setOnClickListener(numberPad);
        n10.setOnClickListener(numberPad);
        n11.setOnClickListener(numberPad);
        n0.setOnClickListener(numberPad);


    }

    void setValues(){
        try{
            requestChargingAmount.setText(extras.getString("charging_amount"));
        }catch (Exception e){}
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
    View.OnClickListener requestBack = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(Request_01_Activity.this,RequestActivity.class);
            Request_01_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener requestContinue = new View.OnClickListener() {
        public void onClick(View v) {

            String price = requestChargingAmount.getText().toString();
            String userName = extras.getString("request_user");

            if (price.equals("")){
                AlertDialog alertDialog = new AlertDialog.Builder(Request_01_Activity.this).create();
                alertDialog.setTitle("Warning!");
                alertDialog.setMessage("Please Fill Amount Field");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }else{
                Intent mainIntent = new Intent(Request_01_Activity.this,Request_02_Activity.class);
                mainIntent.putExtra("charging_amount", price);
                mainIntent.putExtra("request_user", userName);
                Request_01_Activity.this.startActivity(mainIntent);
            }
        }
    };
    View.OnClickListener numberPad = new View.OnClickListener() {
        public void onClick(View v) {
            Object tag = v.getTag();
            try {

                if (v.getTag().toString().equals("x")) {
                    if (amount != "") {
                        amount = amount.substring(0, amount.length() - 1);
                    }

                } else {
                    amount = amount + tag.toString();
                }

                requestChargingAmount.setText(amount);
                requestChargingAmount.setSelection(amount.length());
            }catch (Exception ex){

            }
        }
    };
}
