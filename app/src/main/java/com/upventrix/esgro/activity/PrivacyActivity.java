package com.upventrix.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.upventrix.esgro.R;

public class PrivacyActivity  extends AppCompatActivity {


    Button back;
    TextView policyTXt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        back = findViewById(R.id.privacyBackBtn);
        back.setOnClickListener(backAction);

        policyTXt = findViewById(R.id.policyTxt);
        policyTXt.setMovementMethod(new ScrollingMovementMethod());


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
            Intent mainIntent = new Intent(PrivacyActivity.this, PreferencesActivity.class);
            PrivacyActivity.this.startActivity(mainIntent);
        }
    };
}
