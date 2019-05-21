package com.upventrix.esgro.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.upventrix.esgro.R;

public class ContactUsActivity extends AppCompatActivity {

    Button submit;
    Button done;

    ImageView settings;
    ImageView profileIcon;
    ImageView handshakeIcon;
    ImageView newPostIcon;

    Dialog dialog;

    TextView contactEmailTxt;

    EditText contactMessageTxt;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        idInitialization();
        setListeners();
        setValues();

        dialog = new Dialog(this);
        constraintLayout = findViewById(R.id.activity_contact_us);
        constraintLayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });
    }


    private void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    void idInitialization(){
        submit = findViewById(R.id.submitBtn);
        profileIcon = findViewById(R.id.contactProfileIcon);
        handshakeIcon = findViewById(R.id.contactHandshakeIcon);
        newPostIcon = findViewById(R.id.contactNewPostIcon);
        settings = findViewById(R.id.contactSettingsIcon);
        contactEmailTxt = findViewById(R.id.contactEmailTxt);
        contactMessageTxt = findViewById(R.id.contactMessageTxt);

    }

    void setListeners(){
        submit.setOnClickListener(submitAction);
        profileIcon.setOnClickListener(profile);
        handshakeIcon.setOnClickListener(handshake);
        newPostIcon.setOnClickListener(newAction);
        settings.setOnClickListener(settingsAction);
    }

    void setValues(){

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
    View.OnClickListener submitAction = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.setContentView(R.layout.activity_feedback_alert);

            dialog.show();
            Window window = dialog.getWindow();

            done = window.findViewById(R.id.doneBtn);
            done.setOnClickListener(hideUI);
        }
    };
    View.OnClickListener hideUI = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.dismiss();
        }
    };

    View.OnClickListener profile = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ContactUsActivity.this,ProfileActivity.class);
            ContactUsActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener settingsAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ContactUsActivity.this,HomePageActivity.class);
            ContactUsActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener handshake = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ContactUsActivity.this,DisputeNoHistoryActivity.class);
            ContactUsActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener newAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ContactUsActivity.this,RequestActivity.class);
            ContactUsActivity.this.startActivity(mainIntent);
        }
    };
}
