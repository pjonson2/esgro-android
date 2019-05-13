package com.example.esgro.activity;

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

import com.example.esgro.R;
import com.example.esgro.modals.Bank;

import java.util.ArrayList;
import java.util.List;

public class BankAndCards2Activity extends AppCompatActivity {

    Button card;
    Button bank;
    Button back;
    Button viewMore;
    Button setDefaultBtn;

    ImageView contactIcon;
    ImageView profileIcon;
    ImageView handshakeIcon;
    ImageView newPostIcon;
    ImageView bankCardSettingsIcon;

    TextView plusCrd;
    TextView plusBnk;

    List<Bank> bankList;
    ListView listView;

    int count = 0;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_and_cards2);

        dialog = new Dialog(this);


        listView = findViewById(R.id.dynamicBankListView);
        idInitialization();
        listView.setVisibility(View.INVISIBLE);

        bankList = new ArrayList<>();
        initializeArray();

        BankAndCards2Activity.CustomAdaper customAdaper = new BankAndCards2Activity.CustomAdaper();
        listView.setAdapter(customAdaper);


        setListeners();
        setValues();

    }
    void idInitialization(){
        card = findViewById(R.id.plusCard);
        plusCrd = findViewById(R.id.plusAddCardLbl);
        bank = findViewById(R.id.plusBank);
        plusBnk = findViewById(R.id.plusLinkBankAccountLbl);
        back = findViewById(R.id.bankAndCardBackBtn);
        contactIcon = findViewById(R.id.bankCardContactUsIcon);
        profileIcon = findViewById(R.id.profileIcon);
        bankCardSettingsIcon = findViewById(R.id.bankCardSettingsIcon);
        newPostIcon = findViewById(R.id.bankAndCardNewPostIcon);
        handshakeIcon = findViewById(R.id.bankCardHandshakeIcon);
        viewMore = findViewById(R.id.viewMoreBtn);
        setDefaultBtn = findViewById(R.id.setDefaultBtn);
    }

    void setListeners(){
        card.setOnClickListener(plusCard);
        plusCrd.setOnClickListener(plusCard);
        bank.setOnClickListener(plusBank);
        plusBnk.setOnClickListener(plusBank);
        back.setOnClickListener(backAction);
        contactIcon.setOnClickListener(contactAction);
        profileIcon.setOnClickListener(profileIconAction);
        bankCardSettingsIcon.setOnClickListener(settingsActoin);
        newPostIcon.setOnClickListener(newPostAction);
        handshakeIcon.setOnClickListener(handHsakeAction);
        viewMore.setOnClickListener(viewMoreAction);
        setDefaultBtn.setOnClickListener(setDefaultBtnAction);
    }

    void setValues(){

    }
    void initializeArray(){
        bankList.add(
                new Bank(1,1,"Bank of Ceylon",R.drawable.nopath_360)
        );
        bankList.add(
                new Bank(1,1,"Peoples Bank",R.drawable.bank_of_america)
        );
        bankList.add(
                new Bank(1,1,"Nations Trust Bank",R.drawable.nopath_blue)
        ); bankList.add(
                new Bank(1,1,"Bank of Ceylon",R.drawable.nopath_360)
        );
        bankList.add(
                new Bank(1,1,"Peoples Bank",R.drawable.bank_of_america)
        );
        bankList.add(
                new Bank(1,1,"Nations Trust Bank",R.drawable.nopath_blue)
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
    View.OnClickListener plusCard = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(BankAndCards2Activity.this, AddCardActivity.class);
            mainIntent.putExtra("identifier","BankAndCards2Activity");
            BankAndCards2Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener plusBank = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(BankAndCards2Activity.this, BankListActivity.class);
            mainIntent.putExtra("identifier","BankAndCards2Activity");
            BankAndCards2Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener backAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(BankAndCards2Activity.this, HomePageActivity.class);
            BankAndCards2Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener viewMoreAction = new View.OnClickListener() {
        public void onClick(View v) {
            count++;
            if (count==1) {
                listView.setVisibility(View.VISIBLE);
            }else{
                listView.setVisibility(View.INVISIBLE);
                count = 0;
            }
        }
    };



    View.OnClickListener contactAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(BankAndCards2Activity.this, ContactUsActivity.class);
            BankAndCards2Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener profileIconAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(BankAndCards2Activity.this, ProfileActivity.class);
            BankAndCards2Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener settingsActoin = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(BankAndCards2Activity.this, HomePageActivity.class);
            BankAndCards2Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener newPostAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(BankAndCards2Activity.this,RequestActivity.class);
            BankAndCards2Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener handHsakeAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(BankAndCards2Activity.this,DisputeNoHistoryActivity.class);
            BankAndCards2Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener setDefaultBtnAction = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.setContentView(R.layout.activity_set_defalut_alert);

            Window window = dialog.getWindow();
            ListView listView1;
            listView1 = window.findViewById(R.id.dynamicBankList);
            BankAndCards2Activity.CustomAdaper customAdaper = new BankAndCards2Activity.CustomAdaper();
            listView1.setAdapter(customAdaper);
            dialog.show();
            Button close = window.findViewById(R.id.closeBtn);
            close.setOnClickListener(closeOfAlert);
        }
    };
    View.OnClickListener closeOfAlert = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.dismiss();
        }
    };
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
            convertView = getLayoutInflater().inflate(R.layout.activity_bank_card,null);

            TextView bankNameView = convertView.findViewById(R.id.bankNameTxt);
            ImageView bankImg = convertView.findViewById(R.id.bankImage);
            Bank bank = bankList.get(position);
//            bankImg.setImageResource(bank.getImage());
            bankNameView.setText(bank.getUserid());
            return convertView;
        }
    }
}
