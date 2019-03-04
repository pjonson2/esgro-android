package com.example.esgro.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.esgro.R;

public class LaunchedActivity extends AppCompatActivity {

    private Button getStartedBtn;
    private Button signInBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        ConstraintLayout.LayoutParams params = getLayoutInflater()

        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launched);
        getStartedBtn = findViewById(R.id.getStartBtn1);
        signInBtn = findViewById(R.id.signBtn);
        changeBehaviours();

        getStartedBtn.setOnClickListener(getStart);
        signInBtn.setOnClickListener(signIn);

    }

    public void changeBehaviours(){
        getStartedBtn.setTextColor(Color.parseColor("#5BDA31"));
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

    View.OnClickListener getStart = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(LaunchedActivity.this,SignUpActivity.class);
            LaunchedActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener signIn = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(LaunchedActivity.this,SignInActivity.class);
            LaunchedActivity.this.startActivity(mainIntent);
        }
    };
}
