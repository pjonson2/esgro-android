package com.example.esgro.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.esgro.R;
import com.example.esgro.activity.alert.NotificationAlert;
import com.example.esgro.modals.Notification;

import java.util.ArrayList;
import java.util.List;

public class DisputeDetails_No_History_Activity extends AppCompatActivity {

    Button back;
    Button chat;

    ImageView contactIcon;
    ImageView profileIcon;
    ImageView handshakeIcon;
    ImageView newPostIcon;
    ImageView settings;
    ImageView okIconImge;
    ImageView noDisputeCancelBtn;
    ImageView noDisputeContactBtn;
    ImageView getNoDisputeUserImg;
    ImageView disputeNotification;

    TextView disputeNoUserNameTxt;
    TextView disputeNoUserPriceTxt;
    TextView disputeNoUserDaysTxt;
    TextView disputeNoHistoryDescription;
    TextView disputeReservePrice;
    TextView historyField;

    Bundle extras;
    Dialog dialog;
    private final int SPLASH_DISPLAY_LENGTH = 4000;
    private final int SPLASH_DISPLAY_LENGTH_1 = 2000;
    String value = "";
    String disputeDays="";
    String disputePrice="";
    Bitmap bitmap;
    List<Notification> notificationList;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disputes_details_no_history);
        extras = getIntent().getExtras();

        idInitialization();
        setListeners();
        setValues();

        dialog = new Dialog(this);

    }

    void idInitialization(){

        contactIcon = findViewById(R.id.noDisputesContactIcon);
        profileIcon = findViewById(R.id.noDisputesProfileIcon);
        handshakeIcon = findViewById(R.id.noDisputesHandshakeIcon);
        newPostIcon = findViewById(R.id.noDisputesNewPostIcon);
        settings = findViewById(R.id.noDisputesSettingsIcon);
        back = findViewById(R.id.noDisputeDetailsBack);
        okIconImge = findViewById(R.id.okIconImge);
        noDisputeCancelBtn = findViewById(R.id.noDisputeCancelBtn);
        chat = findViewById(R.id.chatIcon2);
        getNoDisputeUserImg = findViewById(R.id.disputeNoUserCardImg);
        disputeNoUserNameTxt = findViewById(R.id.disputeNoUserNameTxt);
        disputeNoUserPriceTxt = findViewById(R.id.disputePrice);
        disputeNoUserDaysTxt = findViewById(R.id.disputeDay);
        disputeNoHistoryDescription = findViewById(R.id.disputeNoHistoryDescriptionTxt);
        disputeReservePrice = findViewById(R.id.disputeReservePriceTxt);
        noDisputeContactBtn = findViewById(R.id.disputeContactBtn);
        historyField = findViewById(R.id.historyField);
        disputeNotification = findViewById(R.id.disputeNotification);

    }

    void setListeners(){

        contactIcon.setOnClickListener(contactUs);
        profileIcon.setOnClickListener(profile);
        handshakeIcon.setOnClickListener(handshake);
        newPostIcon.setOnClickListener(newAction);
        settings.setOnClickListener(home);
        back.setOnClickListener(backToTimeLine);
        okIconImge.setOnClickListener(submitAction);
        noDisputeCancelBtn.setOnClickListener(cancelAction);
        chat.setOnClickListener(chatAction);
        noDisputeContactBtn.setOnClickListener(contact);
        disputeReservePrice.setOnClickListener(reserve);
        disputeNotification.setOnClickListener(notification);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void setValues(){

        bitmap = getIntent().getParcelableExtra("BitmapImage");
        value = extras.getString("disputeListName");
        disputeDays = extras.getString("disputeListDays");
        disputePrice = extras.getString("disputeListPrice");

        disputeNoUserNameTxt.setText(value);
        disputeNoUserDaysTxt.setText(disputeDays);
        disputeNoUserPriceTxt.setText(disputePrice);
        getNoDisputeUserImg.setImageBitmap(bitmap);
        historyField.setVisibility(View.INVISIBLE);



        if (Double.parseDouble(disputePrice)>0){
            disputeNoUserPriceTxt.setTextColor(getResources().getColor(R.color.lightGreen));
            okIconImge.setEnabled(false);
            okIconImge.setImageDrawable(getDrawable(R.drawable.ok_gray));
        }else{
            disputeNoUserPriceTxt.setTextColor(Color.RED);
            if(disputeDays.equals("waiting") || disputeDays.equals("Canceled") || disputeDays.equals("Completed") ){
                disputeNoUserPriceTxt.setTextColor(Color.GRAY);
                disputeNoUserDaysTxt.setTextColor(Color.GRAY);
            }

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


            weLbl.setText(disputePrice);
            esgroLbl.setText(disputePrice);
            themLbl.setText(disputePrice);

            new Handler().postDelayed(new Runnable(){


                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void run() {

                    if(Double.parseDouble(disputePrice)<0 && disputeDays.equals("waiting")){
                        esgroLbl.setTextColor(Color.parseColor("#929AAB"));
                        weLbl.setTextColor(Color.parseColor("#7FE239"));
                        okIconImge.setImageDrawable(getDrawable(R.drawable.ok_gray));
                        disputeNoUserPriceTxt.setTextColor(getResources().getColor(R.color.lightGreen));
                    }
                    if(Double.parseDouble(disputePrice)<0){
                        esgroLbl.setTextColor(Color.parseColor("#929AAB"));
                        weLbl.setTextColor(Color.parseColor("#7FE239"));
                        okIconImge.setImageDrawable(getDrawable(R.drawable.ok_gray));
                        disputeNoUserPriceTxt.setTextColor(getResources().getColor(R.color.lightGreen));
                    }
                }
            }, SPLASH_DISPLAY_LENGTH_1);

            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    dialog.dismiss();
                    okIconImge.setEnabled(false);
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
            Intent mainIntent = new Intent(DisputeDetails_No_History_Activity.this,ContactUsActivity.class);
            DisputeDetails_No_History_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener notification = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.setContentView(R.layout.activity_notification_alert);
            dialog.show();
            Window window = dialog.getWindow();
            Button close = window.findViewById(R.id.feedbackClose);

            loadNotifications(window);
            close.setOnClickListener(closeNotificationUI);

        }
    };
    View.OnClickListener closeNotificationUI = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.dismiss();
        }
    };
    View.OnClickListener reserve = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.setContentView(R.layout.activity_reserve_alert);
            dialog.show();
            Window window = dialog.getWindow();
            Button button = window.findViewById(R.id.feedbackClose);
            button.setOnClickListener(reserveAlertClose);

        }
    };
    View.OnClickListener reserveAlertClose = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.dismiss();
        }
    };

    View.OnClickListener chatAction = new View.OnClickListener() {
        public void onClick(View v) {

            Intent intent = new Intent(DisputeDetails_No_History_Activity.this,ChatActivity.class);
            intent.putExtra("disputeListName", value);
            intent.putExtra("disputeListPrice", disputePrice);
            intent.putExtra("disputeListDays", disputeDays);
            intent.putExtra("flowOfEvent","DisputeDetails_No_History_Activity");
            intent.putExtra("BitmapImage", bitmap);
            DisputeDetails_No_History_Activity.this.startActivity(intent);
        }
    };
    View.OnClickListener profile = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_No_History_Activity.this,ProfileActivity.class);
            DisputeDetails_No_History_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener home = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_No_History_Activity.this,HomePageActivity.class);
            DisputeDetails_No_History_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener backToTimeLine = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_No_History_Activity.this,DisputeNoHistoryActivity.class);
            DisputeDetails_No_History_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener handshake = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_No_History_Activity.this,DisputeNoHistoryActivity.class);
            DisputeDetails_No_History_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener newAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeDetails_No_History_Activity.this,RequestActivity.class);
            DisputeDetails_No_History_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener contact = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.setContentView(R.layout.activity_contact_us_popup);
            dialog.show();
            Window window = dialog.getWindow();
            Button button;
        }
    };

    void loadNotifications(Window window){
        notificationList = new ArrayList<>();
        initializeArray();

        ListView listView = window.findViewById(R.id.notificationListView);

        DisputeDetails_No_History_Activity.CustomAdaper customAdaper = new DisputeDetails_No_History_Activity.CustomAdaper();
        listView.setAdapter(customAdaper);
    }
    void initializeArray(){

        notificationList.add(new Notification("dispute notifications","",""));

        notificationList.add(new Notification("dispute notifications","",""));

        notificationList.add(new Notification("dispute notifications","",""));

        notificationList.add(new Notification("dispute notifications","",""));

        notificationList.add(new Notification("dispute notifications","",""));

        notificationList.add(new Notification("dispute notifications","",""));

        notificationList.add(new Notification("dispute notifications","",""));

        notificationList.add(new Notification("dispute notifications","",""));

        notificationList.add(new Notification("dispute notifications","",""));

        notificationList.add(new Notification("dispute notifications","",""));

        notificationList.add(new Notification("dispute notifications","",""));


    }
    class CustomAdaper extends BaseAdapter {

        @Override
        public int getCount() {
            return notificationList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.activity_notification_card,null);

            return convertView;
        }
    }
}
