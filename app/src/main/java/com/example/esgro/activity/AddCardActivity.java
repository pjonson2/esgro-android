package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.esgro.R;

public class AddCardActivity extends AppCompatActivity {
    Button back;
    Button addcrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_credit_debit);

        back = findViewById(R.id.addCardBackBtn);
        back.setOnClickListener(addCardBAck);

        addcrd = findViewById(R.id.addCrdBtn);
        addcrd.setOnClickListener(addCardAction);

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
    View.OnClickListener addCardBAck = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(AddCardActivity.this, BankAndCards2Activity.class);
            AddCardActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener addCardAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(AddCardActivity.this, HomePageActivity.class);
            AddCardActivity.this.startActivity(mainIntent);
        }
    };
}
