package com.upventrix.esgro.activity.alert;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.upventrix.esgro.R;

public class RequestSentAlert extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_sent_alert);


    }
}
