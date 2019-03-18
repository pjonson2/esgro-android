package com.example.esgro.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.esgro.R;

public class ChatActivity extends AppCompatActivity {

    Button back;
    RadioButton disputeRadioBtn;
    Button close;
    Button feedBackSendBtn;

    ImageView handshake;
    ImageView contactUs;
    ImageView newPost;
    ImageView profileIcon;
    ImageView settingsIcon;

    Bundle extras;
    String flowOfEvent="";

    TextView chatUserName;

    EditText chatTxt;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        extras = getIntent().getExtras();

        idInitialization();
        setListeners();
        setValues();

        dialog = new Dialog(this);

    }

    void idInitialization(){
        back = findViewById(R.id.chatBackBtn);
        disputeRadioBtn = findViewById(R.id.disputeRadioBtn);
        handshake = findViewById(R.id.chatHandshakeIcon);
        newPost = findViewById(R.id.chatbankAndCardNewPostIcon);
        profileIcon = findViewById(R.id.chatprofileIcon);
        settingsIcon = findViewById(R.id.chatSettingsIcon);
        contactUs = findViewById(R.id.chatContactUsIcon);
        chatUserName = findViewById(R.id.chatUserName);
        chatTxt = findViewById(R.id.chatTxt);
        feedBackSendBtn = findViewById(R.id.feedBackSendBtn);
    }

    void setListeners(){
        handshake.setOnClickListener(handHsakeAction);
        newPost.setOnClickListener(newPostAction);
        profileIcon.setOnClickListener(profileIconAction);
        settingsIcon.setOnClickListener(settingsActoin);
        contactUs.setOnClickListener(contactAction);
        back.setOnClickListener(backAction);
        disputeRadioBtn.setOnClickListener(radioAction);
        feedBackSendBtn.setOnClickListener(send);
    }

    void setValues(){
        chatUserName.setText(extras.getString("disputeListName"));
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
            String value = extras.getString("disputeListName");
            String disputeDays = extras.getString("disputeListDays");
            String disputePrice = extras.getString("disputeListPrice");
            Bitmap bitmap = getIntent().getParcelableExtra("BitmapImage");

            flowOfEvent = extras.getString("flowOfEvent");
            System.out.println("flowOfEvent   "  +flowOfEvent);
            Intent intent;
            if(flowOfEvent.equals("DisputeDetails_01_Activity")){
                 intent = new Intent(ChatActivity.this, DisputeDetails_01_Activity.class);
            }else{
                 intent = new Intent(ChatActivity.this, DisputeDetails_No_History_Activity.class);
            }


            intent.putExtra("disputeListName", value);
            intent.putExtra("disputeListPrice", disputePrice);
            intent.putExtra("disputeListDays", disputeDays);
            intent.putExtra("BitmapImage", bitmap);

            ChatActivity.this.startActivity(intent);
        }
    };
    View.OnClickListener radioAction = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.setContentView(R.layout.activity_warning_alert);
            dialog.show();
            Window window = dialog.getWindow();

            close = window.findViewById(R.id.warnigCloseBtn);
            close.setOnClickListener(hideUI);

            disputeRadioBtn.setSelected(true);
            disputeRadioBtn.setBackgroundResource(R.drawable.light_red_button);
            disputeRadioBtn.setTextColor(Color.WHITE);
        }
    };
    View.OnClickListener hideUI = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.dismiss();
        }
    };

    View.OnClickListener contactAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ChatActivity.this, ContactUsActivity.class);
            ChatActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener profileIconAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ChatActivity.this, ProfileActivity.class);
            ChatActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener settingsActoin = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ChatActivity.this, HomePageActivity.class);
            ChatActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener newPostAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ChatActivity.this,RequestActivity.class);
            ChatActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener handHsakeAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(ChatActivity.this,DisputeNoHistoryActivity.class);
            ChatActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener send = new View.OnClickListener() {
        public void onClick(View v) {
//            Intent mainIntent = new Intent(ChatActivity.this,DisputeNoHistoryActivity.class);
//            ChatActivity.this.startActivity(mainIntent);
        }
    };
}
