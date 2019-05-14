package com.upventrix.esgro.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.User;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.services.UserService;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterVerificationActivity extends AppCompatActivity {

    Button nextBtn;
    Button back;

    EditText n1TXt;
    EditText n2Txt;
    EditText n3Txt;
    EditText n4Txt;

    TextView timerView;
    UserService service = null;

    Bundle extras;
    int verification_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_verification);

        idInitialization();
        setListeners();
        setValues();

    }

    void idInitialization() {
        nextBtn = findViewById(R.id.enterVerificNxtBtn2);
        back = findViewById(R.id.enterVerificBackBtn);
        n1TXt = findViewById(R.id.enterNumber1Txt);
        n2Txt = findViewById(R.id.enterNumber2Txt);
        n3Txt = findViewById(R.id.enterNumber3Txt);
        n4Txt = findViewById(R.id.enterNumber4Txt);
        timerView = findViewById(R.id.enterTimerLbl);
        service = Config.getInstance().create(UserService.class);
        extras = getIntent().getExtras();

    }

    void setListeners() {
        nextBtn.setOnClickListener(nextActoin);
        back.setOnClickListener(backAction);
        n1TXt.addTextChangedListener(checkNumbers);
        n2Txt.addTextChangedListener(checkNumbers);
        n3Txt.addTextChangedListener(checkNumbers);
        n4Txt.addTextChangedListener(checkNumbers);


        n1TXt.addTextChangedListener(n1Change);
        n2Txt.addTextChangedListener(n2Change);
        n3Txt.addTextChangedListener(n3Change);
        n4Txt.addTextChangedListener(n4Change);

        verification_id = extras.getInt("verification_id");

    }

    void setValues() {

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

    View.OnClickListener nextActoin = new View.OnClickListener() {
        public void onClick(View v) {

            if (n1TXt.getText().toString().equals("") || n2Txt.getText().toString().equals("") || n3Txt.getText().toString().equals("") || n4Txt.getText().toString().equals("")) {

            } else {

                int pin = Integer.parseInt(n1TXt.getText() + "" + n2Txt.getText() + "" + n3Txt.getText() + "" + n4Txt.getText());

                Call<JsonObject> userCall = service.confirm(
                            new User(
                                     verification_id,
                                     pin
                            ));
                    userCall.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            String status = null;

                            try {
                                status = response.body().get("status").getAsString();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (status.equals("success")){

                                vewAlert("Successfully","Press ok to continue",EnterVerificationActivity.this);
                            }else{
                                vewAlert("Warnings","Failed to verifying your code ",EnterVerificationActivity.this);
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });
                }
            }
    };
    View.OnClickListener backAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(EnterVerificationActivity.this, MobileVerificationActivity.class);
            EnterVerificationActivity.this.startActivity(mainIntent);
        }
    };
    TextWatcher checkNumbers = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            System.out.println("beforeTextChanged");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (n1TXt.getText().toString().equals("") || n2Txt.getText().toString().equals("") || n3Txt.getText().toString().equals("") || n4Txt.getText().toString().equals("")) {
                nextBtn.setBackgroundResource(R.drawable.round_gray_nxt_icon);

            } else {
                nextBtn.setBackgroundResource(R.drawable.round_nxt_icon);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

            System.out.println("afterTextChanged");

        }
    };

    TextWatcher n1Change = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!n1TXt.getText().equals("")){
                n2Txt.requestFocus();
            }

        }
    };

    TextWatcher n2Change = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!n2Txt.getText().equals("")){
                n3Txt.requestFocus();
            }

        }
    };

    TextWatcher n3Change = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!n3Txt.getText().equals("")){
                n4Txt.requestFocus();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    };

    TextWatcher n4Change = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void vewAlert(final String title, String message, Context context){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // re-direct next form
                        if (title.equals("Successfully")){
                            Intent mainIntent = new Intent(EnterVerificationActivity.this, CompleteProfileActivity.class);
                            EnterVerificationActivity.this.startActivity(mainIntent);
                        }
                    }
                });
        alertDialog.show();
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