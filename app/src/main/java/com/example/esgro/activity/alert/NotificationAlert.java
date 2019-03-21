package com.example.esgro.activity.alert;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.esgro.R;
import com.example.esgro.activity.DisputeActivity;
import com.example.esgro.modals.Dispute;
import com.example.esgro.modals.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationAlert extends AppCompatActivity {
    List<Notification> notificationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_alert);

        notificationList = new ArrayList<>();
        initializeArray();

        ListView listView = findViewById(R.id.notificationListView);

        NotificationAlert.CustomAdaper customAdaper = new NotificationAlert.CustomAdaper();
        listView.setAdapter(customAdaper);

    }
    void initializeArray(){

        notificationList.add(new Notification("","",""));

        notificationList.add(new Notification("","",""));

        notificationList.add(new Notification("","",""));

        notificationList.add(new Notification("","",""));

        notificationList.add(new Notification("","",""));

        notificationList.add(new Notification("","",""));

        notificationList.add(new Notification("","",""));

        notificationList.add(new Notification("","",""));

        notificationList.add(new Notification("","",""));

        notificationList.add(new Notification("","",""));

        notificationList.add(new Notification("","",""));


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



    class CustomAdaper extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
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
