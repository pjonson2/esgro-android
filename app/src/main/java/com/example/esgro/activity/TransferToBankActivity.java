package com.example.esgro.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.esgro.R;

public class TransferToBankActivity extends AppCompatActivity {

    Button back;
    Button transferBtn;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_to_bank);
        transferBtn = findViewById(R.id.transferBtn);
        transferBtn.setOnClickListener(transfer);

        dialog = new Dialog(this);


    }
    View.OnClickListener transfer = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.setContentView(R.layout.activity_transfer_success_alert);
            dialog.show();
            Window window = dialog.getWindow();
        }
    };
}
