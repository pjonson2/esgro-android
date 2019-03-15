package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.esgro.R;
import com.example.esgro.modals.Request;
import java.util.ArrayList;
import java.util.List;

public class RequestActivity extends AppCompatActivity {

    Button back;
    List<Request> requestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        idInitialization();
        setListeners();
        setValues();

    }
    void idInitialization(){
        requestList = new ArrayList<>();
        initializeArray();
        back = findViewById(R.id.requestBackBtn);
        ListView listView = findViewById(R.id.dynamicRequestList);


        RequestActivity.CustomAdaper customAdaper = new RequestActivity.CustomAdaper();
        listView.setAdapter(customAdaper);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                TextView textView = view.findViewById(R.id.requestUserName);
                String name = textView.getText().toString();

                Intent intent = new Intent(RequestActivity.this, Request_01_Activity.class);
                startActivity(intent);
            }
        });

    }

    void setListeners(){
        back.setOnClickListener(backFromRequest);
    }

    void setValues(){

    }


        void initializeArray() {

            requestList.add(
                    new Request("Pamela anderson", "3 days ago", R.drawable.user1)
            );
            requestList.add(
                    new Request("Nikkal simonze", "6 days ago", R.drawable.user2)
            );
            requestList.add(
                    new Request("Katharina Kaif", "1 Week ago", R.drawable.user3)
            );
            requestList.add(
                    new Request("Nicole minaj", "3 days ago", R.drawable.user4)
            );
            requestList.add(
                    new Request("camilla cibello", "2 hours ago", R.drawable.user5)
            );
            requestList.add(
                    new Request("Selena gomez", "3 days ago", R.drawable.user6)
            );
            requestList.add(
                    new Request("Maria shomnix", "5 days ago", R.drawable.user7)
            );
            requestList.add(
                    new Request("joudge bush", "1 weeks ago", R.drawable.user8)
            );
            requestList.add(
                    new Request("SGrahams Smith", "3 days ago", R.drawable.user9)
            );
            requestList.add(
                    new Request("Michel clark", "1 days ago", R.drawable.user1)
            );
            requestList.add(
                    new Request("James Anderson", "2 days ago", R.drawable.user6)
            );

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

    View.OnClickListener backFromRequest = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(RequestActivity.this, DisputeNoHistoryActivity.class);
            RequestActivity.this.startActivity(mainIntent);
        }
    };

    class CustomAdaper extends BaseAdapter {

        @Override
        public int getCount() {
            return requestList.size();
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
            convertView = getLayoutInflater().inflate(R.layout.activity_request_card,null);

            TextView bankNameView = convertView.findViewById(R.id.requestUserName);
            ImageView requestImg = convertView.findViewById(R.id.requestUserImg);
            TextView daysTxt = convertView.findViewById(R.id.requestDays);

            Request request = requestList.get(position);
            requestImg.setImageResource(request.getImage());
            bankNameView.setText(request.getName());
            daysTxt.setText(request.getDays());
            return convertView;
        }
    }

}




