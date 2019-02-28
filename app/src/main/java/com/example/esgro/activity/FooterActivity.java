package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.esgro.R;

public class FooterActivity extends AppCompatActivity {

    ImageView contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footer);
        contact = findViewById(R.id.footerContactIcon);
        contact.setOnClickListener(contacts);

    }
    View.OnClickListener contacts = new View.OnClickListener() {
        public void onClick(View v) {
//            Intent mainIntent = new Intent(FooterActivity.this, ContactUsActivity.class);
//            FooterActivity.this.startActivity(mainIntent);
        }
    };
}
