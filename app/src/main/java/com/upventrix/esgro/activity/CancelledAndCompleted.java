package com.upventrix.esgro.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.Dispute;

import java.util.ArrayList;
import java.util.List;

public class CancelledAndCompleted extends AppCompatActivity {

    List<Dispute> disputeList;
    Button canceledBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled_completed);

        idInitialization();
        setListeners();
        setValues();

    }

    void idInitialization(){

        canceledBackBtn = findViewById(R.id.canceledBackBtn);
        canceledBackBtn.setOnClickListener(backAction);

        disputeList = new ArrayList<>();
        initializeArray();

        ListView listView = findViewById(R.id.cancelledListView);

        CancelledAndCompleted.CustomAdaper customAdaper = new CancelledAndCompleted.CustomAdaper();
        listView.setAdapter(customAdaper);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position,
//                                    long id) {

//                TextView textView = view.findViewById(R.id.disputeUserName);
//                String name = textView.getText().toString();
//
//                ImageView imageView = view.findViewById(R.id.disputeUserImage);
//                imageView.buildDrawingCache();
//                Bitmap bitmap = imageView.getDrawingCache();
//
//                TextView disputePriceTxt = view.findViewById(R.id.disputePriceTxt);
//                String disputePrice = disputePriceTxt.getText().toString();
//
//                TextView disputeDaysTxt = view.findViewById(R.id.disputeDaysTxt);
//                String disputeDays = disputeDaysTxt.getText().toString();
//
//
//                Intent intent = new Intent(DisputeActivity.this, DisputeDetails_01_Activity.class);
//
//                intent.putExtra("disputeListName", name);
//                intent.putExtra("disputeListPrice", disputePrice);
//                intent.putExtra("disputeListDays", disputeDays);
//                intent.putExtra("BitmapImage", bitmap);

//                startActivity(intent);
//            }
//        });
    }

    void setListeners(){

    }

    void setValues(){

    }

    void initializeArray(){

        disputeList.add(
                new Dispute("Pamel anderson","","-238.12","Completed",R.drawable.user1)
        );
        disputeList.add(
                new Dispute("Nikkal simonze","","-200.32","Completed",R.drawable.user2)
        );
        disputeList.add(
                new Dispute("sunny leon","","-164.12","Completed",R.drawable.user3)
        );
        disputeList.add(
                new Dispute("Nicole minaj","","-60.12","Completed",R.drawable.user4)
        );
        disputeList.add(
                new Dispute("camilla cibello","","+422.22","Completed",R.drawable.user5)
        );
        disputeList.add(
                new Dispute("Selena gomez","","+76.32","Completed",R.drawable.user6)
        );
        disputeList.add(
                new Dispute("Maria shomnix","","+255.43","Completed",R.drawable.user7)
        );
        disputeList.add(
                new Dispute("joudge bush","","+432.12","Cancelled",R.drawable.user8)
        );
        disputeList.add(
                new Dispute("SGrahams Smith","","+98.32","Cancelled",R.drawable.user9)
        );
        disputeList.add(
                new Dispute("Michel clark","","+234.32","Cancelled",R.drawable.user1)
        );
        disputeList.add(
                new Dispute("James Anderson","","-455.32","Cancelled",R.drawable.user6)
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

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.activity_canceled_completed_card,null);

            TextView bankNameView = convertView.findViewById(R.id.cancelledUserName);
            TextView disputePrice = convertView.findViewById(R.id.cancelledPrice);
            TextView disputedays = convertView.findViewById(R.id.cancelledStatus);
            ImageView deisputeImg = convertView.findViewById(R.id.cancelledUserImge);

            Dispute dispute = disputeList.get(position);

            deisputeImg.setImageResource(dispute.getImage());
            bankNameView.setText(dispute.getName());
            disputedays.setText(dispute.getDays());
            disputePrice.setText(dispute.getPrice());



            return convertView;
        }
    }
    View.OnClickListener backAction = new View.OnClickListener() {
        public void onClick(View v) {

            Intent mainIntent = new Intent(CancelledAndCompleted.this,HomePageActivity.class);
            CancelledAndCompleted.this.startActivity(mainIntent);
        }
    };
}
