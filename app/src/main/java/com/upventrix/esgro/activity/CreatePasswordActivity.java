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

import com.upventrix.esgro.R;

public class CreatePasswordActivity  extends AppCompatActivity {


    Button back;
    Button updateBtn;
    ConstraintLayout constraintLayout;

    EditText confirmTxt;
    EditText newPwTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
        back = findViewById(R.id.createPwBackBtn);
        back.setOnClickListener(backAction);

        confirmTxt = findViewById(R.id.confirmPwTxt);
        newPwTxt = findViewById(R.id.newPwTxt);

        updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(updateAction);

        constraintLayout = findViewById(R.id.activity_create_password);
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
            CreatePasswordActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener updateAction = new View.OnClickListener() {
        public void onClick(View v) {

        }
    };
}
