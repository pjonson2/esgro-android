package com.upventrix.esgro.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.Bank;
import com.upventrix.esgro.modals.CardDetails;
import com.upventrix.esgro.modals.UniqueID;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.LocalData;
import com.upventrix.esgro.services.BankService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferToBankActivity extends AppCompatActivity {

    Button back;
    Button transferBtn;
    Dialog dialog;

    List<Bank> bankList;
    Bitmap bitmap;

    View toLinkBankView;
    ListView listView;
    private BankService bankService;
    ConstraintLayout constraintLayout;
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
        bankService = Config.getInstance().create(BankService.class);
        constraintLayout = findViewById(R.id.emptyView);

        bankList = new ArrayList<>();
        initializeArray();

        dialog = new Dialog(this);
        listView = findViewById(R.id.transferBankList);

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
//            dialog.dismiss();
            Intent mainIntent = new Intent(TransferToBankActivity.this, ProfileActivity.class);
            TransferToBankActivity.this.startActivity(mainIntent);
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

            Intent mainIntent = new Intent(TransferToBankActivity.this, PlaidActivity.class);
            mainIntent.putExtra("identifier","TransferToBankActivity");
            TransferToBankActivity.this.startActivity(mainIntent);
        }
    };

    void initializeArray(){
        {
            {
                final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String userData = new LocalData().getlocalData(sharedPref, "userdata");
                int userid = 0;
                try {
                    JSONObject jsonObj = new JSONObject(userData);
                    userid = jsonObj.getInt("user_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Call<JsonObject> all = bankService.getAll(new UniqueID(userid));
                all.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        String status = "";
                        try {
                            status = response.body().get("status").getAsString();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (status.toString().equals("success")) {

                            JsonArray bankArrayList = response.body().getAsJsonArray("data");


                            if (bankArrayList.size()==0){
                                constraintLayout.setVisibility(View.VISIBLE);
                                return;
                            }
                            bankList.clear();


                            for (JsonElement value :bankArrayList) {

                                 Bank b = new Bank();
                                 b.setAcc_name(value.getAsJsonObject().get("acc_name").getAsString());
                                 b.setAcc_id(value.getAsJsonObject().get("acc_id").getAsInt());
                                 bankList.add(b);
                            }

                            TransferToBankActivity.CustomAdaper customAdaper = new TransferToBankActivity.CustomAdaper();
                            listView.setAdapter(customAdaper);
                        }else{
                            constraintLayout.setVisibility(View.VISIBLE);
                            System.out.println("ERROR");
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        System.out.println("ERROR "+t);
                    }
                });
            }
        }

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
            TextView bankDetailsTxt = convertView.findViewById(R.id.bankDetailsTxt);
            Bank bank = bankList.get(position);
//            bankImg.setImageResource(bank.getImage());
            bankNameView.setText(bank.getAcc_name());
            bankDetailsTxt.setText("Acc no. "+bank.getAccount_num());
            return convertView;
        }
    }
}
