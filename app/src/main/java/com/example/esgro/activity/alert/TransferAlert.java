package com.example.esgro.activity.alert;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.esgro.R;
import com.example.esgro.activity.TransferToBankActivity;
import com.example.esgro.activity.TransferToCardActivity;

public class TransferAlert extends AppCompatActivity {

//    Button close;
//    Button testingBtn;
//
//    Dialog dialog;
//
//    ConstraintLayout bankLayout;
//    ConstraintLayout cardLayout;
//
//    TextView bankAccountLbl;
//    TextView cardLbl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_alert);

//        idInitialization();
//        setListeners();
//        setValues();
    }
//
//    void idInitialization(){
//        bankLayout = findViewById(R.id.bankAccountView);
//        cardLayout = findViewById(R.id.cardView);
//        bankAccountLbl = findViewById(R.id.bankAccountLbl);
//        cardLbl = findViewById(R.id.cardLbl);
//        testingBtn = findViewById(R.id.testingBtn);
//    }
//
//    void setListeners(){
//        cardLbl.setOnClickListener(cardLayoutAction);
//        bankAccountLbl.setOnClickListener(bankLayoutAction);
//        testingBtn.setOnClickListener(testingAction);
//    }
//
//    void setValues(){
//
//    }
//    View.OnClickListener cardLayoutAction = new View.OnClickListener() {
//        public void onClick(View v) {
//            System.out.println("clicked");
//            Intent mainIntent = new Intent(TransferAlert.this, TransferToCardActivity.class);
//            TransferAlert.this.startActivity(mainIntent);
//        }
//    };
//    View.OnClickListener bankLayoutAction = new View.OnClickListener() {
//        public void onClick(View v) {
//            Intent mainIntent = new Intent(TransferAlert.this, TransferToBankActivity.class);
//            TransferAlert.this.startActivity(mainIntent);
//        }
//    };
//    View.OnClickListener testingAction = new View.OnClickListener() {
//        public void onClick(View v) {
//            System.out.println("Clicking");
//            Intent mainIntent = new Intent(TransferAlert.this, TransferToBankActivity.class);
//            TransferAlert.this.startActivity(mainIntent);
//        }
//    };
}
