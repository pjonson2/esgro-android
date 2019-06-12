package com.upventrix.esgro.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.rafaelbarbosatec.archivimentview.AchievementView;
import com.upventrix.esgro.R;

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
    Button iClick;

    EditText requestChargingAmount;
    EditText reserveTxt;

    String amount="$";
    String amount2="$";

    Bundle extras;
    Dialog dialog;
    AchievementView achievementView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_1);
        extras = getIntent().getExtras();

        idInitialization();
        setListeners();
        setValues();
        requestChargingAmount.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = requestChargingAmount.getInputType(); // backup the input type
                requestChargingAmount.setInputType(InputType.TYPE_NULL); // disable soft input
                requestChargingAmount.onTouchEvent(event); // call native handler
                requestChargingAmount.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });

        reserveTxt.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = reserveTxt.getInputType(); // backup the input type
                reserveTxt.setInputType(InputType.TYPE_NULL); // disable soft input
                reserveTxt.onTouchEvent(event); // call native handler
                reserveTxt.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });
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
        iClick = findViewById(R.id.iClick);
        achievementView = findViewById(R.id.achievementView);
        reserveTxt = findViewById(R.id.reserveTxt);
        dialog = new Dialog(this);


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
        iClick.setOnClickListener(iClickAction);

    }



    void setValues(){
        try{
            requestChargingAmount.setText(extras.getString("charging_amount"));
            reserveTxt.setText(extras.getString("reserve_amount"));
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
    View.OnClickListener iClickAction = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.setContentView(R.layout.activity_iclick_reserve_alert);
            dialog.show();
            Window window = dialog.getWindow();

            Button closeBtn = window.findViewById(R.id.reserveCloseBtn);
            closeBtn.setOnClickListener(reserveCloseAction);
        }
    };
    View.OnClickListener reserveCloseAction = new View.OnClickListener() {
        public void onClick(View v) {dialog.dismiss();
        }
    };
    View.OnClickListener requestContinue = new View.OnClickListener() {
        public void onClick(View v) {

            String price = requestChargingAmount.getText().toString();
            String reserve_price = reserveTxt.getText().toString();
            String userName = extras.getString("request_user");
            String userId = extras.getString("request_userId");

            if (price.equals("")){
                new  ToastActivity().showFailed(
                        achievementView,
                        "Warnings!",
                        "PLease Fill Amount!"
                );
            }else{
                Intent mainIntent = new Intent(Request_01_Activity.this,Request_02_Activity.class);
                mainIntent.putExtra("charging_amount", price);
                mainIntent.putExtra("request_user", userName);
                mainIntent.putExtra("reserve_amount", reserve_price);
                mainIntent.putExtra("request_userId",userId);
                Request_01_Activity.this.startActivity(mainIntent);
            }
        }
    };
    View.OnClickListener numberPad = new View.OnClickListener() {
        public void onClick(View v) {
            Object tag = v.getTag();

            if  (requestChargingAmount.isFocused()){

                if (amount.equals("")) amount = "$";

                if (v.getTag().toString().equals(".")) {

                    int i = countChar(requestChargingAmount.getText().toString(), '.');
                    if (i>0) return;

                }

                try {

                    if (v.getTag().toString().equals("x")) {
                        if (amount != "") {
                            amount = amount.substring(0, amount.length() - 1);
                        }else{
                        }

                    } else {
                        amount = amount + tag.toString();
                    }

                    requestChargingAmount.setText(amount);
                    requestChargingAmount.setSelection(amount.length());
                }catch (Exception ex){

                }
            }

            if(reserveTxt.isFocused()){

                if (amount2.equals("")) amount2 = "$";

                if (v.getTag().toString().equals(".")) {

                    int i = countChar(reserveTxt.getText().toString(), '.');
                    if (i>0) return;

                }

                try {

                    if (v.getTag().toString().equals("x")) {
                        if (amount2 != "") {
                            amount2 = amount2.substring(0, amount2.length() - 1);
                        }else{
                        }

                    } else {
                        amount2 = amount2 + tag.toString();
                    }

                    reserveTxt.setText(amount2);
                    reserveTxt.setSelection(amount2.length());
                }catch (Exception ex){

                }
            }

        }
    };
    public int countChar(String str, char c)
    {
        int count = 0;

        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == c)
            count++;
        }

        return count;
    }
}
