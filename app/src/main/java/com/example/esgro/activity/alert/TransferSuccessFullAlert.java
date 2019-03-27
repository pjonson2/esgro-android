package com.example.esgro.activity.alert;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.esgro.R;

public class TransferSuccessFullAlert extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_success_alert);

    }
}
