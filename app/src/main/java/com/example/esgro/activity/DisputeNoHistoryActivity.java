package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.esgro.R;
import com.example.esgro.modals.Dispute;

import java.util.ArrayList;
import java.util.List;

public class DisputeNoHistoryActivity extends AppCompatActivity {

    List<Dispute> disputeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispute_shake);
        disputeList = new ArrayList<>();
        initializeArray();

        ListView listView = findViewById(R.id.dynamicShakeListView);

        DisputeNoHistoryActivity.CustomAdaper customAdaper = new DisputeNoHistoryActivity.CustomAdaper();
        listView.setAdapter(customAdaper);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                TextView textView = view.findViewById(R.id.disputeUserName);
                String name = textView.getText().toString();

                Intent intent = new Intent(DisputeNoHistoryActivity.this, DisputeDetails_No_History_Activity.class);
                intent.putExtra("disputeListName", name);
                startActivity(intent);


            }
        });
    }

    void initializeArray(){

        disputeList.add(
                new Dispute("Pamela anderson","","","",R.drawable.user1)
        );
        disputeList.add(
                new Dispute("Nikkal simonze","","","",R.drawable.user2)
        );
        disputeList.add(
                new Dispute("Nations Trust Bank","","","",R.drawable.user3)
        );
        disputeList.add(
                new Dispute("Nicole minaj","","","",R.drawable.user4)
        );
        disputeList.add(
                new Dispute("camilla cibello","","","",R.drawable.user5)
        );
        disputeList.add(
                new Dispute("Selena gomez","","","",R.drawable.user6)
        );
        disputeList.add(
                new Dispute("Maria shomnix","","","",R.drawable.user7)
        );
        disputeList.add(
                new Dispute("joudge bush","","","",R.drawable.user8)
        );
        disputeList.add(
                new Dispute("SGrahams Smith","","","",R.drawable.user9)
        );
        disputeList.add(
                new Dispute("Michel clark","","","",R.drawable.user1)
        );
        disputeList.add(
                new Dispute("James Anderson","","","",R.drawable.user6)
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
    class CustomAdaper extends BaseAdapter {

        @Override
        public int getCount() {
            return disputeList.size();
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
            convertView = getLayoutInflater().inflate(R.layout.activity_dispute_card,null);

            TextView bankNameView = convertView.findViewById(R.id.disputeUserName);
            ImageView deisputeImg = convertView.findViewById(R.id.disputeUserImage);

            Dispute dispute = disputeList.get(position);
            deisputeImg.setImageResource(dispute.getImage());
            bankNameView.setText(dispute.getName());
            return convertView;
        }
    }
}
