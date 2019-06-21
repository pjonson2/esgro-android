package com.upventrix.esgro.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.gson.JsonObject;
import com.rafaelbarbosatec.archivimentview.AchievementView;
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.UniqueEmail;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.services.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity  extends AppCompatActivity {


    Button back;
    Button submit;
    ConstraintLayout constraintLayout;
    EditText emailTxt;
    UserService userService;
    AchievementView achievementView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        constraintLayout = findViewById(R.id.activity_forgot_password);
        back = findViewById(R.id.forgatBackBtn);
        back.setOnClickListener(backAction);

        submit = findViewById(R.id.submitBtn);
        submit.setOnClickListener(submitAction);

        emailTxt = findViewById(R.id.emailTxt);
        userService = Config.getInstance().create(UserService.class);

        achievementView = findViewById(R.id.achievementView);
        progressBar = findViewById(R.id.progressBar);

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
        submit.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                onWindowFocusChanged(true);
                return false;
            }
        });

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
            Intent mainIntent = new Intent(ForgotPasswordActivity.this, SignInActivity.class);
            ForgotPasswordActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener submitAction = new View.OnClickListener() {
        public void onClick(View v) {
            String email = emailTxt.getText().toString();
            if(email.equals("")){
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            submit.setEnabled(false);
            Call<JsonObject> forgot = userService.forgot(new UniqueEmail(email));
            forgot.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    boolean status = false;
                    boolean msgSent = false;
                    String mobile = "";
                    int veri_id = 0;
                    try {
                        status = response.body().get("status").getAsBoolean();
                        System.out.println(status);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(status){
                        msgSent = response.body().get("msg_sent").getAsBoolean();
                        System.out.println(msgSent);
                        if (msgSent){
                            progressBar.setVisibility(View.GONE);
                            mobile = response.body().get("mobile").getAsString();
                            veri_id =  response.body().get("veri_id").getAsInt();
                            Intent mainIntent = new Intent(ForgotPasswordActivity.this, ResetCodeActivity.class);
                            mainIntent.putExtra("last_Digits",mobile);
                            mainIntent.putExtra("email",email);
                            mainIntent.putExtra("veri_id",veri_id);
                            ForgotPasswordActivity.this.startActivity(mainIntent);
                        }else{
                            progressBar.setVisibility(View.GONE);
                            new ToastActivity().showFailed(
                                    achievementView,
                                    "Warnings !",
                                    "Email Verification Failed");
                            submit.setEnabled(true);
                        }
                    }else{
                        progressBar.setVisibility(View.GONE);
                        new ToastActivity().showFailed(
                               achievementView,
                               "Warnings !",
                               "Email Verification Failed");
                        submit.setEnabled(true);

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    new ToastActivity().showFailed(
                            achievementView,
                            "Warnings !",
                            "Email Verification Failed");
                    submit.setEnabled(true);
                }
            });
        }
    };

}
