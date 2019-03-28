package com.example.esgro.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.esgro.R;
import com.example.esgro.modals.Bank;

import java.util.ArrayList;
import java.util.List;

public class TransferToBankActivity extends AppCompatActivity {

    Button back;
    Button transferBtn;
    Dialog dialog;

    List<Bank> bankList;
    Bitmap bitmap;

    View toLinkBankView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_to_bank);
        transferBtn = findViewById(R.id.transferBtn);
        transferBtn.setOnClickListener(transfer);

        back = findViewById(R.id.transferBackBtn3);
        back.setOnClickListener(backAction);

        toLinkBankView = findViewById(R.id.toLinkBankView);
        toLinkBankView.setOnClickListener(toLinkBank);

        bankList = new ArrayList<>();
        initializeArray();

        dialog = new Dialog(this);
        ListView listView = findViewById(R.id.transferBankList);

        TransferToBankActivity.CustomAdaper customAdaper = new TransferToBankActivity.CustomAdaper();
        listView.setAdapter(customAdaper);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
//
//                TextView textView  = view.findViewById(R.id.bankNameText);
//                String name = textView.getText().toString();
//
//                ImageView imageView  = view.findViewById(R.id.bankIconImg);
//                imageView.buildDrawingCache();
//                bitmap = imageView.getDrawingCache();
//
//                Intent intent = new Intent(BankListActivity.this, LinkBankAccountActivity.class);
//
//                intent.putExtra("bankListName", name);
//                intent.putExtra("bankListImage", bitmap);
//
//                startActivity(intent);
            }
        });


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
            dialog.dismiss();
        }
    };
    View.OnClickListener backAction = new View.OnClickListener() {
        public void onClick(View v) {

            Intent mainIntent = new Intent(TransferToBankActivity.this, ProfileActivity.class);
            TransferToBankActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener toLinkBank = new View.OnClickListener() {
        public void onClick(View v) {

            Intent mainIntent = new Intent(TransferToBankActivity.this, BankListActivity.class);
            mainIntent.putExtra("identifier","TransferToBankActivity");
            TransferToBankActivity.this.startActivity(mainIntent);
        }
    };

    void initializeArray(){
        bankList.add(
                new Bank("Bank of Ceylon",R.drawable.nopath_360)
        );
        bankList.add(
                new Bank("Peoples Bank",R.drawable.bank_of_america)
        );
        bankList.add(
                new Bank("Nations Trust Bank",R.drawable.nopath_blue)
        ); bankList.add(
                new Bank("Bank of Ceylon",R.drawable.nopath_360)
        );
        bankList.add(
                new Bank("Peoples Bank",R.drawable.bank_of_america)
        );
        bankList.add(
                new Bank("Nations Trust Bank",R.drawable.nopath_blue)
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
            convertView = getLayoutInflater().inflate(R.layout.activity_bank_card,null);

            TextView bankNameView = convertView.findViewById(R.id.bankNameTxt);
            ImageView bankImg = convertView.findViewById(R.id.bankImage);
            Bank bank = bankList.get(position);
            bankImg.setImageResource(bank.getImage());
            bankNameView.setText(bank.getBankName());
            return convertView;
        }
    }
}
