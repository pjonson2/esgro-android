package com.upventrix.esgro.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rafaelbarbosatec.archivimentview.AchievementView;
import com.upventrix.esgro.R;

public class Request_02_Activity extends AppCompatActivity {

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
    Button n11;

    String days="";
    Bundle extras;
    String amount="";
    String reserveAmount = "";
    String request_user= "";
    String userId = "";

    EditText holdingDays;
    AchievementView achievementView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_2);
        extras = getIntent().getExtras();

        idInitialization();
        setListeners();
        setValues();
        holdingDays.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = holdingDays.getInputType(); // backup the input type
                holdingDays.setInputType(InputType.TYPE_NULL); // disable soft input
                holdingDays.onTouchEvent(event); // call native handler
                holdingDays.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });
    }

//    @SuppressLint("ClickableViewAccessibility")
    void idInitialization(){
        back = findViewById(R.id.request2BackBtn);
        continues = findViewById(R.id.reqiest2ContinueBtn);
        holdingDays = findViewById(R.id.requestHoldingDaysTxt);
        achievementView = findViewById(R.id.achievementView);
        n1 =  findViewById(R.id.n1);
        n2 =  findViewById(R.id.n2);
        n3 =  findViewById(R.id.n3);
        n4 =  findViewById(R.id.n4);
        n5 =  findViewById(R.id.n5);
        n6 =  findViewById(R.id.n6);
        n7 =  findViewById(R.id.n7);
        n8 =  findViewById(R.id.n8);
        n9 =  findViewById(R.id.n9);
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
        n11.setOnClickListener(numberPad);
        n0.setOnClickListener(numberPad);
    }

    void setValues(){
        amount = extras.getString("charging_amount");
        request_user = extras.getString("request_user");
        reserveAmount = extras.getString("reserve_amount");
        userId = extras.getString("request_userId");
        System.out.println("request_userId    "+userId);
        try{
            days = extras.getString("holding_days");
            if (days.equals(null)){
                days = "";
            }
        }catch (Exception e){
            days = "";
        }
        holdingDays.setText(days);
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
            String holdings = holdingDays.getText().toString();
            Intent mainIntent = new Intent(Request_02_Activity.this, Request_01_Activity.class);
            mainIntent.putExtra("charging_amount",amount);
            mainIntent.putExtra("request_user",extras.getString("request_user"));
            mainIntent.putExtra("reserve_amount",reserveAmount);
            mainIntent.putExtra("request_userId",userId);
            Request_02_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener requestContinue = new View.OnClickListener() {
        public void onClick(View v) {
            String holdings = holdingDays.getText().toString();

            if (!holdings.equals("")){
                Intent mainIntent = new Intent(Request_02_Activity.this, Request_03_Activity.class);
                mainIntent.putExtra("holding_days",holdings);
                mainIntent.putExtra("request_user",request_user);
                mainIntent.putExtra("charging_amount",amount);
                mainIntent.putExtra("reserve_amount",reserveAmount);
                mainIntent.putExtra("request_userId",userId);
                Request_02_Activity.this.startActivity(mainIntent);
            }else{
                new  ToastActivity().showFailed(
                        achievementView,
                        "Warnings!",
                        "Please Fill Holding Period Field!"
                );
            }
        }
    };
    View.OnClickListener numberPad = new View.OnClickListener() {
        public void onClick(View v) {
            Object tag = v.getTag();
                try {
                    if (v.getTag().toString().equals("x")){
                        if(days!=""){
                            days = days.substring(0, days.length() - 1);
                        }
                    }else{
                        days = days +tag.toString();
                    }

                    holdingDays.setText(days);
                    holdingDays.setSelection(days.length());
                }catch (Exception ex){

                }
        }
    };
}
