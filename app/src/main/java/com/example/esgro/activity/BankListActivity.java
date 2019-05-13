package com.example.esgro.activity;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.example.esgro.modals.Bank;

import java.util.ArrayList;
import java.util.List;

public class BankListActivity extends AppCompatActivity {

    List<Bank> bankList;

    Button back;
    Bitmap bitmap;
    String identifier="";

    public final static String EXTRA_MESSAGE = "com.example.ListViewTest.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_list);


        identifier = getIntent().getStringExtra("identifier");

        idInitialization();
        setListeners();
        setValues();





        ListView listView = findViewById(R.id.dynamicListView);

        CustomAdaper customAdaper = new CustomAdaper();
        listView.setAdapter(customAdaper);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                TextView textView  = view.findViewById(R.id.bankNameText);
                String name = textView.getText().toString();

                ImageView imageView  = view.findViewById(R.id.bankIconImg);
                imageView.buildDrawingCache();
                bitmap = imageView.getDrawingCache();

                Intent intent = new Intent(BankListActivity.this, LinkBankAccountActivity.class);

                intent.putExtra("bankListName", name);
                intent.putExtra("bankListImage", bitmap);
                intent.putExtra("identifier", identifier);

                startActivity(intent);
            }
        });
    }
    void idInitialization(){
        back = findViewById(R.id.link_bank_account_backBtn);
        bankList = new ArrayList<>();
        initializeArray();

    }

    void setListeners(){
        back.setOnClickListener(backtoHome);
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
        );
        bankList.add(
                new Bank(1,1,"DfCC Bank",R.drawable.nopath_copy2x)
        );
        bankList.add(
                new Bank(1,1,"NSB",R.drawable.nopath_360)
        );
        bankList.add(
                new Bank(1,1,"Union Bank",R.drawable.nopath_blue)
        );
        bankList.add(
                new Bank(1,1,"RDB",R.drawable.nopath_360)
        );
        bankList.add(
                new Bank(1,1,"Commercial Bank",R.drawable.nopath_copy2x)
        );
        bankList.add(
                new Bank(1,1,"Sampth Bank",R.drawable.nopath_360)
        );
        bankList.add(
                new Bank(1,1,"Bank Of Asia",R.drawable.nopath_360)
        );
        bankList.add(
                new Bank(1,1,"Bank Of America",R.drawable.nopath_blue)
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

    class CustomAdaper extends BaseAdapter{

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
            convertView = getLayoutInflater().inflate(R.layout.activity_bank_layout,null);

            TextView bankNameView = convertView.findViewById(R.id.bankNameText);
            ImageView bankImg = convertView.findViewById(R.id.bankIconImg);
            Bank bank = bankList.get(position);
            bankImg.setImageResource(bank.getUserid());
            bankNameView.setText(bank.getAcc_name());
            return convertView;
        }
    }
    View.OnClickListener backtoHome = new View.OnClickListener() {
        public void onClick(View v) {

            if (identifier.equals("TransferToBankActivity")){
                Intent mainIntent = new Intent(BankListActivity.this, TransferToBankActivity.class);
                BankListActivity.this.startActivity(mainIntent);
            }

            if (identifier.equals("BankAndCards2Activity")){
                Intent mainIntent = new Intent(BankListActivity.this, BankAndCards2Activity.class);
                BankListActivity.this.startActivity(mainIntent);
            }

            if (identifier.equals("CompleteProfileActivity")){
                Intent mainIntent = new Intent(BankListActivity.this, CompleteProfileActivity.class);
                BankListActivity.this.startActivity(mainIntent);
            }

        }
    };

}
