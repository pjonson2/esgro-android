package com.example.esgro.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.esgro.R;
import com.example.esgro.modals.Adapter;

public class BankListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String [] item = {"Bank1","Bank2","Bank3","Bank4","Bank5"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_list);
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Adapter(this,item));
    }
}
