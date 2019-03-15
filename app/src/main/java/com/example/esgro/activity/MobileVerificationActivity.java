package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.esgro.R;

public class MobileVerificationActivity  extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button backBtn;
    Button nextBtn;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);

        idInitialization();
        setListeners();
        setValues();

        loadSpinner();

    }
    void idInitialization(){
        backBtn = findViewById(R.id.verificationBackBtn);
        nextBtn = findViewById(R.id.mobileVerificNxtBtn);
        spinner = findViewById(R.id.mobileVerificSpinner);
    }

    void setListeners(){
        backBtn.setOnClickListener(backAction);
        nextBtn.setOnClickListener(nxtAction);
    }

    void setValues(){

    }

    void loadSpinner(){

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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
            Intent mainIntent = new Intent(MobileVerificationActivity.this,SignUpActivity.class);
            MobileVerificationActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener nxtAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(MobileVerificationActivity.this,EnterVerificationActivity.class);
            MobileVerificationActivity.this.startActivity(mainIntent);
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("Select a item from spinner   1");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        System.out.println("Select a item from spinner bo  ");
    }
}
