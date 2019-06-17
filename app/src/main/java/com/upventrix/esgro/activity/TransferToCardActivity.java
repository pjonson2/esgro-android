package com.upventrix.esgro.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.Bank;

import java.util.ArrayList;
import java.util.List;

public class TransferToCardActivity  extends AppCompatActivity {

    Button back;
    Button transferBtn;

    Dialog dialog;

    List<Bank> bankList;

    View toAddCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_to_card);
        transferBtn = findViewById(R.id.transferBtn);
        transferBtn.setOnClickListener(transfer);

        back = findViewById(R.id.transferBackBtn);
        back.setOnClickListener(backAction);

        toAddCardView = findViewById(R.id.toAddCardView);
        toAddCardView.setOnClickListener(toAddCard);

        bankList = new ArrayList<>();
        initializeArray();

        dialog = new Dialog(this);
        ListView listView = findViewById(R.id.transferToCardList);

        TransferToCardActivity.CustomAdaper customAdaper = new TransferToCardActivity.CustomAdaper();
        listView.setAdapter(customAdaper);


    }

    View.OnClickListener transfer = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.setContentView(R.layout.activity_transfer_success_alert);
            dialog.show();
            Window window = dialog.getWindow();

            Button button = window.findViewById(R.id.dismissBtn);
            button.setOnClickListener(dismissAction);
        }
    };
    View.OnClickListener dismissAction = new View.OnClickListener() {
        public void onClick(View v) {
//            dialog.dismiss();
            Intent mainIntent = new Intent(TransferToCardActivity.this, ProfileActivity.class);
            TransferToCardActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener toAddCard = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(TransferToCardActivity.this, AddCardActivity.class);
            mainIntent.putExtra("identifier","TransferToCardActivity");
            TransferToCardActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener backAction = new View.OnClickListener() {
        public void onClick(View v) {

            Intent mainIntent = new Intent(TransferToCardActivity.this, ProfileActivity.class);
            TransferToCardActivity.this.startActivity(mainIntent);
        }
    };
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
    void initializeArray() {
        bankList.add(
                new Bank(1,1,"Bank of Ceylon", R.drawable.nopath_360,true)
        );
        bankList.add(
                new Bank(1,1,"Peoples Bank", R.drawable.bank_of_america,true)
        );
        bankList.add(
                new Bank(1,1,"Nations Trust Bank", R.drawable.nopath_blue,false)
        );
        bankList.add(
                new Bank(1,1,"DfCC Bank", R.drawable.nopath_copy2x,false)
        );


    }

    class CustomAdaper extends BaseAdapter {

        @Override
        public int getCount() {
            return bankList.size();
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
            convertView = getLayoutInflater().inflate(R.layout.activity_bank_card, null);

            TextView bankNameView = convertView.findViewById(R.id.bankNameTxt);
            TextView bankDetails = convertView.findViewById(R.id.bankDetailsTxt);

            ImageView bankImg = convertView.findViewById(R.id.bankImage);
            Bank bank = bankList.get(position);
//            bankImg.setImageResource(bank.getImage());
            bankNameView.setText(bank.getUserid());
            bankDetails.setText("Card no. ends in 5651");
            return convertView;
        }
    }
}

