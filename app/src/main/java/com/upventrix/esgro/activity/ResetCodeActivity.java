package com.upventrix.esgro.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.rafaelbarbosatec.archivimentview.AchievementView;
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.UniqueEmail;
import com.upventrix.esgro.modals.User;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.services.UserService;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetCodeActivity  extends AppCompatActivity {


    Button back;
    ConstraintLayout constraintLayout;
    Button resetNxtBtn;
    Button resendBtn;
    Dialog dialog;
    Bundle extras;
    String lastDigit = "";
    String email = "";
    int veri_id= 0;
    TextView txt148;
    TextView timerView;
    UserService userService;
    String finish = "no";
    EditText pinTxt;
    AchievementView achievementView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_code);
        extras = getIntent().getExtras();
        back = findViewById(R.id.resetCodeBackBtn);
        back.setOnClickListener(backAction);

        resetNxtBtn = findViewById(R.id.resetNxtBtn);
        resetNxtBtn.setOnClickListener(nxtAction);

        resendBtn = findViewById(R.id.resendBtn);
        resendBtn.setOnClickListener(resendAction);
        txt148 = findViewById(R.id.textView148);

        dialog = new Dialog(this);
        timerView = findViewById(R.id.countTimeLbl);

        achievementView = findViewById(R.id.achievementView);

        pinTxt = findViewById(R.id.editText8);

        resendBtn.setEnabled(false);

        lastDigit = extras.getString("last_Digits");
        email = extras.getString("email");
        veri_id = extras.getInt("veri_id");
        System.out.println(lastDigit);
        txt148.setText("A text message with the verification code was just send to "+lastDigit);
        userService = Config.getInstance().create(UserService.class);

        constraintLayout = findViewById(R.id.activity_rest_code);
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
        resetNxtBtn.setOnTouchListener(new View.OnTouchListener()
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
                resendBtn.setEnabled(true);
            }
        }.start();
    }
    private void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
            Intent mainIntent = new Intent(ResetCodeActivity.this, ForgotPasswordActivity.class);
            ResetCodeActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener resendAction = new View.OnClickListener() {
        public void onClick(View v) {

            dialog.setContentView(R.layout.activity_warning_alert);
            dialog.show();
            Window window = dialog.getWindow();

            Button proceedBtn = window.findViewById(R.id.proceedBtn);
            Button cancel = window.findViewById(R.id.cancelBtn);
            Button close = window.findViewById(R.id.warnigCloseBtn);

            close.setOnClickListener(hideUI);
            cancel.setOnClickListener(hideUI);
            proceedBtn.setOnClickListener(proceedAction);

        }
        View.OnClickListener hideUI = new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        };
        View.OnClickListener proceedAction = new View.OnClickListener() {
            public void onClick(View v) {
                resendBtn.setEnabled(false);
                System.out.println("EMAIL  ........ "+email);
                Call<JsonObject> forgot = userService.forgot(new UniqueEmail(email));
                forgot.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        boolean status = false;
                        try {
                            status = response.body().get("status").getAsBoolean();
                            System.out.println(status);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(status){
                            dialog.dismiss();
                            veri_id =  response.body().get("veri_id").getAsInt();
                            resendBtn.setEnabled(false);
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
                                    resendBtn.setEnabled(true);
                                }
                            }.start();
                        }else{
                            new ToastActivity().showFailed(
                                    achievementView,
                                    "Warnings !",
                                    "Email Verification Failed");
                            resendBtn.setEnabled(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        resendBtn.setEnabled(true);
                    }
                });
            }
        };
    };
    View.OnClickListener nxtAction = new View.OnClickListener() {
        public void onClick(View v) {
            resendBtn.setEnabled(false);
            if(pinTxt.getText().toString().equals("")){
                return;
            }
            int pin = Integer.parseInt(pinTxt.getText().toString());
            Call<JsonObject> jsonObjectCall = userService.verifyPin(new User(
                    veri_id,
                    pin));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    boolean status = false;
                    try {
                        status = response.body().get("status").getAsBoolean();
                        System.out.println(status);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(status){
                        veri_id = response.body().get("veri_id").getAsInt();

                        Intent mainIntent = new Intent(ResetCodeActivity.this, CreatePasswordActivity.class);
                        mainIntent.putExtra("veri_id",veri_id);
                        mainIntent.putExtra("last_Digits",lastDigit);
                        mainIntent.putExtra("email",email);

                        ResetCodeActivity.this.startActivity(mainIntent);
//                        new ToastActivity().showOK(achievementView,"Successfully","Email verified Successfully",ResetCodeActivity.this, CreatePasswordActivity.class);
                    }else{
                        new ToastActivity().showFailed(achievementView,"Warnings!","Failed to verify email");
                        resendBtn.setEnabled(true);
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    new ToastActivity().showFailed(achievementView,"Warnings!","Failed to verify email");
                    resendBtn.setEnabled(true);
                }
            });

        }
    };
}
