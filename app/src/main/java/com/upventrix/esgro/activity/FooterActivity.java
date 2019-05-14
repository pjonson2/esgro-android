package com.upventrix.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.upventrix.esgro.R;

public class FooterActivity extends AppCompatActivity {

    ImageView contact;
    ImageView settingIconInFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footer);

        idInitialization();
//        setListeners();
        setValues();

    }

    void idInitialization(){
        contact = findViewById(R.id.footerContactIcon);
        settingIconInFooter = findViewById(R.id.settingIconInFooter);
    }

    void setListeners(){
        contact.setOnClickListener(contacts);
        settingIconInFooter.setOnClickListener(settingAction);
    }

    void setValues(){

    }

    View.OnClickListener contacts = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(FooterActivity.this, ContactUsActivity.class);
            FooterActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener settingAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(FooterActivity.this, HomePageActivity.class);
            FooterActivity.this.startActivity(mainIntent);
        }
    };
}
