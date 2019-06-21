package com.upventrix.esgro.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
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

import com.google.gson.JsonObject;
import com.rafaelbarbosatec.archivimentview.AchievementView;
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.Password;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.LocalData;
import com.upventrix.esgro.services.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckPasswordActivity extends AppCompatActivity {

    Button back;
    Button update;

    EditText currentPassword;
    EditText newPassword;
    EditText confirmpassword;
    private UserService userService;
    ConstraintLayout constraintLayout;
    Drawable background;
    AchievementView achievementView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_password);

        idInitialization();
        setListeners();
        setValues();
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
    }


    private void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    void idInitialization(){
        back = findViewById(R.id.checkPasswordBackBtn);
        update = findViewById(R.id.updatePasswordBtn);
        currentPassword = findViewById(R.id.currentPasswordTxt);
        newPassword = findViewById(R.id.newPasswordTxt);
        confirmpassword = findViewById(R.id.confirmPasswordTxt);
        constraintLayout = findViewById(R.id.activity_check_password);
        userService = Config.getInstance().create(UserService.class);
        achievementView = findViewById(R.id.achievementView);
    }

    void setListeners(){
        back.setOnClickListener(backAction);
        update.setOnClickListener(updatePswrdAction);
        newPassword.addTextChangedListener(newPwChange);
        confirmpassword.addTextChangedListener(confirmPwChange);
    }
    TextWatcher newPwChange = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            System.out.println("beforeTextChanged");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            System.out.println("onTextChanged");
        }

        @Override
        public void afterTextChanged(Editable s) {
            System.out.println("afterTextChanged");
            String searchTxt = newPassword.getText().toString();
            ShapeDrawable shape = new ShapeDrawable(new RectShape());
            if(searchTxt.toString().length()>=4){
                newPassword.setBackground(background);
                update.setEnabled(true);
            }else{
                shape.getPaint().setColor(Color.RED);
                shape.getPaint().setStyle(Paint.Style.STROKE);
                shape.getPaint().setStrokeWidth(1);
                newPassword.setBackground(shape);
                update.setEnabled(false);
            }
        }
    };
    TextWatcher confirmPwChange = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            System.out.println("beforeTextChanged");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            System.out.println("onTextChanged");
        }

        @Override
        public void afterTextChanged(Editable s) {
            System.out.println("afterTextChanged");
            String searchTxt = confirmpassword.getText().toString();
            ShapeDrawable shape = new ShapeDrawable(new RectShape());
            if(searchTxt.toString().length()>=4){
                confirmpassword.setBackground(background);
                update.setEnabled(true);
            }else{
                shape.getPaint().setColor(Color.RED);
                shape.getPaint().setStyle(Paint.Style.STROKE);
                shape.getPaint().setStrokeWidth(1);
                confirmpassword.setBackground(shape);
                update.setEnabled(false);
            }
        }
    };
    void setValues(){
        background = confirmpassword.getBackground();
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
            Intent mainIntent = new Intent(CheckPasswordActivity.this, PreferencesActivity.class);
            CheckPasswordActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener updatePswrdAction = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void onClick(View v) {
            update.setEnabled(false);
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            String userData = new LocalData().getlocalData(sharedPref, "userdata")+"";
            int userid = 0;
            try {
                JSONObject jsonObj = new JSONObject(userData);
                userid = jsonObj.getInt("user_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String currentPw = currentPassword.getText().toString();
            String newPw = newPassword.getText().toString();
            String confirmPw = confirmpassword.getText().toString();


            if (!newPw.equals(confirmPw) || confirmPw.equals("")){
                ShapeDrawable shape = new ShapeDrawable(new RectShape());
                shape.getPaint().setColor(Color.RED);
                shape.getPaint().setStyle(Paint.Style.STROKE);
                shape.getPaint().setStrokeWidth(1);
                newPassword.setBackground(shape);
                confirmpassword.setBackground(shape);
                update.setEnabled(true);
                return;
            }else{
                newPassword.setBackground(background);
                confirmpassword.setBackground(background);
            }

            Call<JsonObject> jsonObjectCall = userService.changeKey(new Password(userid,currentPw,confirmPw));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    String status = "";
                    try {
                        status = response.body().get("status").getAsString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){
                         new  ToastActivity().showOK(
                                achievementView,
                                "Successfully!",
                                "Profile Password changed",
                                CheckPasswordActivity.this,
                                HomePageActivity.class);
                    }else{
                        update.setEnabled(true);
                        new  ToastActivity().showFailed(
                                achievementView,
                                "Warnings!",
                                "Password changing failed");
                     }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    update.setEnabled(true);

                    new  ToastActivity().showFailed(
                            achievementView,
                            "Warnings!",
                            "Password changing failed");

                }
            });
        }
    };

}
