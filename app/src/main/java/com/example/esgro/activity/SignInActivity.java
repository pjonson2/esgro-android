package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.esgro.R;

public class SignInActivity extends AppCompatActivity {

    Button back;
    Button continueBtn;
    Button getStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin );
        back = findViewById(R.id.siginInBackBtn);
        back.setOnClickListener(backAction);

        continueBtn = findViewById(R.id.signInContinueBtn);
        continueBtn.setOnClickListener(continueBtnAction);

        getStart = findViewById(R.id.signIngetStartBtn);
        getStart.setOnClickListener(getStartBtnAction);
        Runnable mNavHider = new Runnable() {
            @Override public void run() {
                onWindowFocusChanged(true);
            }
        };

//        TextViewCompat.setAutoSizeTextTypeUniformWithPresetSizes(int[] presetSizes, int unit);

//// Example
//        int[] sizes = getContext().getResources().getIntArray(R.array.autosize_text_sizes)
//        TextViewCompat.setAutoSizeTextTypeUniformWithPresetSizes(myTextView, sizes, TypedValue.COMPLEX_UNIT_SP);

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
            Intent mainIntent = new Intent(SignInActivity.this,LaunchedActivity.class);
            SignInActivity.this.startActivity(mainIntent);
        }
    };

    View.OnClickListener continueBtnAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(SignInActivity.this,MobileVerificationActivity.class);
            SignInActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener getStartBtnAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(SignInActivity.this,SignUpActivity.class);
            SignInActivity.this.startActivity(mainIntent);
        }
    };
}
