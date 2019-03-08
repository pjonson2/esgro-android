package com.example.esgro.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.esgro.R;

public class ChatActivity extends AppCompatActivity {

    Button back;
    RadioButton disputeRadioBtn;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        back = findViewById(R.id.chatBackBtn);
        back.setOnClickListener(backAction);

        disputeRadioBtn = findViewById(R.id.disputeRadioBtn);
        disputeRadioBtn.setOnClickListener(radioAction);

        dialog = new Dialog(this);


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
            Intent mainIntent = new Intent(ChatActivity.this, DisputeDetails_01_Activity.class);
            ChatActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener radioAction = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.setContentView(R.layout.activity_warning_alert);
            dialog.show();

            disputeRadioBtn.setSelected(true);
            disputeRadioBtn.setBackgroundResource(R.drawable.light_red_button);
            disputeRadioBtn.setTextColor(Color.WHITE);
        }
    };
}
