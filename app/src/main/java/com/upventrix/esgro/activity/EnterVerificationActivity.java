package com.upventrix.esgro.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.rafaelbarbosatec.archivimentview.AchievementView;
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.User;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.LocalData;
import com.upventrix.esgro.services.UserService;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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
    String number ="";
    private Timer myTimer;
    String finish = "no";
    TextView otp;

    ConstraintLayout constraintLayout;
    private final int interval = 1000; // 1 Second
    private Handler handler = new Handler();
    AchievementView achievementView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_verification);

        idInitialization();
        setListeners();
        setValues();

        constraintLayout = findViewById(R.id.activity_enter_verification);
        constraintLayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                onWindowFocusChanged(true);
                return false;
            }
        });
        nextBtn.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                onWindowFocusChanged(true);
                return false;
            }
        });

        new CountDownTimer(120000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                timerView.setText(""+String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                System.out.println("DONE");
                finish = "finished";
            }
        }.start();

//        Timer();
//        myTimer = new Timer();
//        myTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                TimerMethod();
//            }
//
//        }, 0, 1000);
     }
    private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.
        System.out.println("Timer");
        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            //This method runs in the same thread as the UI.
            System.out.println("Timer_Tick");

            //Do something to the UI thread here

        }
    };
    private void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
        achievementView = findViewById(R.id.achievementView);
        extras = getIntent().getExtras();
        otp = findViewById(R.id.otpBtn);

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
        number = extras.getString("number");
        otp.setOnClickListener(otpAction);

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
            System.out.println("Again");
            if (n1TXt.getText().toString().equals("") || n2Txt.getText().toString().equals("") || n3Txt.getText().toString().equals("") || n4Txt.getText().toString().equals("")) {

            } else {
                nextBtn.setEnabled(false);
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
                                new ToastActivity().showOK(
                                        achievementView,
                                        "Successfully!",
                                        "Your contact number verified",
                                        EnterVerificationActivity.this,
                                        CompleteProfileActivity.class);
                             }else{
                                new ToastActivity().showFailed(
                                        achievementView,
                                        "Warnings",
                                        "Failed to verifying your pin"
                                 );
                                 nextBtn.setEnabled(true);
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

    View.OnClickListener otpAction = new View.OnClickListener() {
        public void onClick(View v) {

            if(finish.equals("finished")){
                finish = "no";
                callApi();
                new CountDownTimer(120000, 1000) { // adjust the milli seconds here

                    public void onTick(long millisUntilFinished) {
                        timerView.setText(""+String.format("%d:%d",
                                TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    }

                    public void onFinish() {
                        System.out.println("DONE");
                        finish = "finished";
                    }
                }.start();

            }else{
                System.out.println("Not finished");
            }

        }
    };

    private void callApi() {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userData = new LocalData().getlocalData(sharedPref, "userdata");
        int userid = 0;
        try {
            JSONObject jsonObj = new JSONObject(userData);
            userid = jsonObj.getInt("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<JsonObject> userCall = service.verify(
                new User(
                        "+"+number,
                        userid
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

                    verification_id = response.body().get("verification_id").getAsInt();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

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
            if (!n1TXt.getText().toString().equals("")){
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
            if (!n2Txt.getText().toString().equals("")){
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
            if (!n3Txt.getText().toString().equals("")){
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