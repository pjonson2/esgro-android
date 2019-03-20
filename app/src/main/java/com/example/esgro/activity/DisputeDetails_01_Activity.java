package com.example.esgro.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.esgro.R;

public class    DisputeDetails_01_Activity extends AppCompatActivity {

    Button back;
    Button chat;

    ImageView contactIcon;
    ImageView profileIcon;
    ImageView handshakeIcon;
    ImageView newPostIcon;
    ImageView settings;
    ImageView likeIconImg;
    ImageView disputeCancelBtn;
    ImageView disputeUserCardImg;
    ImageView disputeContactBtn;

    Dialog dialog;

    TextView disputeDaysTxt;
    TextView disputePriceTxt;
    TextView disputeDescriptionView;
    TextView disputeReservePriceView;
    TextView userName;

    Bundle extras;
    String flowOfEvent="";
    Bitmap bitmap;
    private final int SPLASH_DISPLAY_LENGTH = 4000;
    static String name = "";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disputes_details);
        extras = getIntent().getExtras();

        idInitialization();
        setListeners();
        setValues();

        dialog = new Dialog(this);

    }


    void idInitialization(){

        contactIcon = findViewById(R.id.disputesContactIcon);
        profileIcon = findViewById(R.id.disputesProfileIcon);
        handshakeIcon = findViewById(R.id.disputesHandshakeIcon);
        newPostIcon = findViewById(R.id.disputesNewPostIcon);
        settings = findViewById(R.id.disputesSettingsIcon);
        back = findViewById(R.id.disputeDetailsBack);
        likeIconImg = findViewById(R.id.likeIconImg);
        disputeCancelBtn = findViewById(R.id.disputeCancelBtn);
        disputeUserCardImg = findViewById(R.id.disputeNoUserCardImg);
        chat = findViewById(R.id.chatIcon);
        userName = findViewById(R.id.disputeUserName);
        disputeDaysTxt = findViewById(R.id.disputeDay);
        disputePriceTxt = findViewById(R.id.disputePrice);
        disputeDescriptionView = findViewById(R.id.disputeDescriptionView);
        disputeReservePriceView = findViewById(R.id.disputeReservePriceView);
        disputeContactBtn = findViewById(R.id.disputeContactBtn);

    }

    void setListeners(){

        contactIcon.setOnClickListener(contactUs);
        profileIcon.setOnClickListener(profile);
        handshakeIcon.setOnClickListener(handshake);
        newPostIcon.setOnClickListener(newAction);
        settings.setOnClickListener(home);
        back.setOnClickListener(backToTimeLine);
        likeIconImg.setOnClickListener(submitAction);
        disputeCancelBtn.setOnClickListener(cancelAction);
        chat.setOnClickListener(chatAction);
        disputeContactBtn.setOnClickListener(contact);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void setValues(){

        String value = extras.getString("disputeListName");
        String disputeDays = extras.getString("disputeListDays");
        String disputePrice = extras.getString("disputeListPrice");
        bitmap = getIntent().getParcelableExtra("BitmapImage");


        disputeUserCardImg.setImageBitmap(bitmap);
        userName.setText(value);
        disputeDaysTxt.setText(disputeDays);
        disputePriceTxt.setText(disputePrice);

        if (Double.parseDouble(disputePrice)>0){
            likeIconImg.setEnabled(false);
            likeIconImg.setImageDrawable(getDrawable(R.drawable.ok_gray));
        }

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
            dialog.setContentView(R.layout.activity_proessing_alert);
            dialog.show();
            Window window = dialog.getWindow();

            final TextView weLbl = window.findViewById(R.id.weLbl);
            final TextView esgroLbl = window.findViewById(R.id.esgroLbl);
            final TextView themLbl = window.findViewById(R.id.themLbl);

            weLbl.setText(disputePriceTxt.getText().toString());
            esgroLbl.setText(disputePriceTxt.getText().toString());
            themLbl.setText(disputePriceTxt.getText().toString());

                    new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, SPLASH_DISPLAY_LENGTH);
        }
    };

    View.OnClickListener cancelAction = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.setContentView(R.layout.activity_dispute_cancel_alert);
            dialog.show();
            Window window = dialog.getWindow();
            Button button;
            button = window.findViewById(R.id.goBackBtn);
            button.setOnClickListener(hideUI);

        }
     View.OnClickListener hideUI = new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        };
    };

    View.OnClickListener contactUs = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_01_Activity.this,ContactUsActivity.class);
            DisputeDetails_01_Activity.this.startActivity(mainIntent);
        }
    };

    View.OnClickListener profile = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_01_Activity.this,ProfileActivity.class);
            DisputeDetails_01_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener home = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_01_Activity.this,HomePageActivity.class);
            DisputeDetails_01_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener backToTimeLine = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_01_Activity.this,DisputeActivity.class);
            DisputeDetails_01_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener handshake = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_01_Activity.this,DisputeNoHistoryActivity.class);
            DisputeDetails_01_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener newAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_01_Activity.this,RequestActivity.class);
            DisputeDetails_01_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener contact = new View.OnClickListener() {
        public void onClick(View v) {
//            Intent mainIntent = new Intent(DisputeDetails_01_Activity.this,RequestActivity.class);
//            DisputeDetails_01_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener chatAction = new View.OnClickListener() {
        public void onClick(View v) {

            Intent intent = new Intent(DisputeDetails_01_Activity.this,ChatActivity.class);

            intent.putExtra("disputeListName", userName.getText().toString());
            intent.putExtra("disputeListPrice", disputePriceTxt.getText().toString());
            intent.putExtra("disputeListDays", disputeDaysTxt.getText().toString());
            intent.putExtra("flowOfEvent","DisputeDetails_01_Activity");
            intent.putExtra("BitmapImage", bitmap);

            DisputeDetails_01_Activity.this.startActivity(intent);
        }
    };
}
