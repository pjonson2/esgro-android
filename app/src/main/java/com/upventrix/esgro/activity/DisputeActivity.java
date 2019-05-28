package com.upventrix.esgro.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.Dispute;

import java.util.ArrayList;
import java.util.List;

public class DisputeActivity extends AppCompatActivity {

    List<Dispute> disputeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispute);

        idInitialization();
        setListeners();
        setValues();

    }

    void idInitialization(){

        disputeList = new ArrayList<>();
        initializeArray();

        ListView listView = findViewById(R.id.dynamicDisputeListView);

        DisputeActivity.CustomAdaper customAdaper = new DisputeActivity.CustomAdaper();
        listView.setAdapter(customAdaper);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                TextView textView = view.findViewById(R.id.disputeUserName);
                String name = textView.getText().toString();

                ImageView imageView = view.findViewById(R.id.disputeUserImage);
                imageView.buildDrawingCache();
                Bitmap bitmap = imageView.getDrawingCache();

                TextView disputePriceTxt = view.findViewById(R.id.disputePriceTxt);
                String disputePrice = disputePriceTxt.getText().toString();

                TextView disputeDaysTxt = view.findViewById(R.id.disputeDaysTxt);
                String disputeDays = disputeDaysTxt.getText().toString();


                Intent intent = new Intent(DisputeActivity.this, DisputeDetails_01_Activity.class);

                intent.putExtra("disputeListName", name);
                intent.putExtra("disputeListPrice", disputePrice);
                intent.putExtra("disputeListDays", disputeDays);
                intent.putExtra("BitmapImage", bitmap);

                startActivity(intent);
            }
        });
    }

    void setListeners(){

    }

    void setValues(){

    }

    void initializeArray(){




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
            convertView = getLayoutInflater().inflate(R.layout.activity_dispute_card,null);

            TextView bankNameView = convertView.findViewById(R.id.disputeUserName);
            TextView disputePrice = convertView.findViewById(R.id.disputePriceTxt);
            TextView disputedays = convertView.findViewById(R.id.disputeDaysTxt);
            ImageView deisputeImg = convertView.findViewById(R.id.disputeUserImage);

            Dispute dispute = disputeList.get(position);

            bankNameView.setText(dispute.getName());
            disputedays.setText(dispute.getDays());
            disputePrice.setText(dispute.getPrice());

            if(Double.parseDouble(dispute.getPrice())<0){
                disputePrice.setTextColor(Color.RED);
            }
            if(Double.parseDouble(dispute.getPrice())>0){
                disputePrice.setTextColor(getResources().getColor(R.color.lightGreen));
            }

            if (dispute.getDays().equals("Canceled")|| dispute.getDays().equals("Completed")){
                disputePrice.setTextColor(Color.parseColor("#929AAB"));
                convertView.setBackgroundColor(Color.parseColor("#ECECF5"));

            }

            return convertView;
        }
    }
}
