package com.example.esgro.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esgro.R;
import com.example.esgro.modals.Adapter;
import com.example.esgro.modals.Bank;

import java.util.ArrayList;
import java.util.List;

public class BankListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    //    Adapter adapter;
    List<Bank> bankList;
    public final static String EXTRA_MESSAGE = "com.example.ListViewTest.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_list);
        bankList = new ArrayList<>();
        initializeArray();

        ListView listView = findViewById(R.id.dynamicListView);

        CustomAdaper customAdaper = new CustomAdaper();
        listView.setAdapter(customAdaper);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                TextView textView  = view.findViewById(R.id.bankNameText);
                ImageView imageView  = view.findViewById(R.id.bankIconImg);
                String name = textView.getText().toString();
                String imageName = String.valueOf(imageView.getId());
                System.out.println("bank Image "+imageName);

//
                Intent intent = new Intent(BankListActivity.this, LinkBankAccountActivity.class);
                intent.putExtra("bankListName", name);
//                intent.putExtra("bankListImg", imageView.getResources().toString());
                startActivity(intent);

            }
        });



    }


    void initializeArray(){
        bankList.add(
                new Bank("Bank of Ceylon",R.drawable.nopath_360)
        );
        bankList.add(
                new Bank("Peoples Bank",R.drawable.bank_of_america)
        );
        bankList.add(
                new Bank("Nations Trust Bank",R.drawable.nopath_blue)
        );
        bankList.add(
                new Bank("DfCC Bank",R.drawable.nopath_copy2x)
        );
        bankList.add(
                new Bank("NSB",R.drawable.nopath_360)
        );
        bankList.add(
                new Bank("Union Bank",R.drawable.nopath_blue)
        );
        bankList.add(
                new Bank("RDB",R.drawable.nopath_360)
        );
        bankList.add(
                new Bank("Commercial Bank",R.drawable.nopath_copy2x)
        );
        bankList.add(
                new Bank("Sampth Bank",R.drawable.nopath_360)
        );
        bankList.add(
                new Bank("Bank Of Asia",R.drawable.nopath_360)
        );
        bankList.add(
                new Bank("Bank Of America",R.drawable.nopath_blue)
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
            bankImg.setImageResource(bank.getImage());
            bankNameView.setText(bank.getBankName());
            return convertView;
        }
    }

}
