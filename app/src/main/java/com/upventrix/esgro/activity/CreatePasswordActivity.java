package com.upventrix.esgro.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import com.upventrix.esgro.modals.PasswordReset;
import com.upventrix.esgro.modals.User;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.services.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePasswordActivity  extends AppCompatActivity {


    Button back;
    Button updateBtn;
    ConstraintLayout constraintLayout;
    Drawable background;
    EditText confirmTxt;
    EditText newPwTxt;
    Bundle extras;
    int veri_id = 0;
    UserService userService;
    AchievementView achievementView;
    String email = "";
    String lastDigits = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
        extras = getIntent().getExtras();
        back = findViewById(R.id.createPwBackBtn);
        back.setOnClickListener(backAction);
        userService = Config.getInstance().create(UserService.class);
        confirmTxt = findViewById(R.id.confirmPwTxt);
        newPwTxt = findViewById(R.id.newPwTxt);
        background = newPwTxt.getBackground();
        updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(updateAction);

        newPwTxt.addTextChangedListener(newPwChange);
        confirmTxt.addTextChangedListener(confirmPwChange);

        veri_id = extras.getInt("veri_id");
        lastDigits = extras.getString("last_Digits");
        email = extras.getString("email");
        constraintLayout = findViewById(R.id.activity_create_password);
        achievementView = findViewById(R.id.achievementView);
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
            String searchTxt = newPwTxt.getText().toString();
            ShapeDrawable shape = new ShapeDrawable(new RectShape());
            if(searchTxt.toString().length()>=4){
                newPwTxt.setBackground(background);
                updateBtn.setEnabled(true);
            }else{
                shape.getPaint().setColor(Color.RED);
                shape.getPaint().setStyle(Paint.Style.STROKE);
                shape.getPaint().setStrokeWidth(1);
                newPwTxt.setBackground(shape);
                updateBtn.setEnabled(false);
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
            String searchTxt = confirmTxt.getText().toString();
            ShapeDrawable shape = new ShapeDrawable(new RectShape());
            if(searchTxt.toString().length()>=4){
                confirmTxt.setBackground(background);
                updateBtn.setEnabled(true);
            }else{
                shape.getPaint().setColor(Color.RED);
                shape.getPaint().setStyle(Paint.Style.STROKE);
                shape.getPaint().setStrokeWidth(1);
                confirmTxt.setBackground(shape);
                updateBtn.setEnabled(false);
            }
        }
    };

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
            Intent mainIntent = new Intent(CreatePasswordActivity.this, ResetCodeActivity.class);
            mainIntent.putExtra("veri_id",veri_id);
            mainIntent.putExtra("last_Digits",lastDigits);
            mainIntent.putExtra("email",email);
            CreatePasswordActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener updateAction = new View.OnClickListener() {
        public void onClick(View v) {
            String newpw = newPwTxt.getText().toString();
            String confirmPw = confirmTxt.getText().toString();
            updateBtn.setEnabled(false);
            ShapeDrawable shape = new ShapeDrawable(new RectShape());

            if(newpw.equals(confirmPw)){
                newPwTxt.setBackground(background);
                confirmTxt.setBackground(background);

                Call<JsonObject> jsonObjectCall = userService.resetPw(new PasswordReset(veri_id, newpw));
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
                            new ToastActivity().showOK(achievementView,"Successfully","Password successfully changed",CreatePasswordActivity.this, SignInActivity.class);
                        }else{
                            new ToastActivity().showFailed(achievementView,"Warnings!","Password changing failed");
                            updateBtn.setEnabled(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        new ToastActivity().showFailed(achievementView,"Warnings!","Password changing failed");
                        updateBtn.setEnabled(true);
                    }
                });

            }else{
                updateBtn.setEnabled(true);
                shape.getPaint().setColor(Color.RED);
                shape.getPaint().setStyle(Paint.Style.STROKE);
                shape.getPaint().setStrokeWidth(1);
                newPwTxt.setBackground(shape);
                confirmTxt.setBackground(shape);
            }

        }
    };
}
