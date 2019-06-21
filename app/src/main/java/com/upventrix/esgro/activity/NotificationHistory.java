package com.upventrix.esgro.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.upventrix.esgro.R;

import java.text.DecimalFormat;

public class NotificationHistory  extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_history);
        back = findViewById(R.id.historyBtn);
        back.setOnClickListener(backAction);

        setValues();
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

    void setValues(){

//        DisputeNoHistoryActivity.CustomAdaper customAdaper = new DisputeNoHistoryActivity.CustomAdaper();
//        ListView listView = findViewById(R.id.dynamicShakeListView);
//        listView.setAdapter(customAdaper);
    }
    @Override
    public void onRefresh() {
        System.out.println("Refresh");
//        disputeList.clear();
//        setValues();
    }

    View.OnClickListener backAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(NotificationHistory.this, HomePageActivity.class);
            NotificationHistory.this.startActivity(mainIntent);
        }
    };


    class CustomAdaper extends BaseAdapter {

        @Override
        public int getCount() {

            return 0;
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

//            progressBar.setVisibility(View.GONE);
//            swipeRefreshLayout.setRefreshing(false);
            return convertView;
        }
    }
}
