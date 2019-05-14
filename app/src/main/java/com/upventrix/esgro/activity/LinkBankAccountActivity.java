package com.upventrix.esgro.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.Bank;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.LocalData;
import com.upventrix.esgro.services.BankService;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LinkBankAccountActivity extends AppCompatActivity {

    TextView textView;

    ImageView linkedBankImg;

    Button save;
    Button back;

    Bitmap bitmap;

    EditText routingTxt;
    EditText accountNumberTxt;

    String identifier="";

    private BankService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_bank);


        idInitialization();
        setListeners();
        setValues();

    }

    void idInitialization(){
        back = findViewById(R.id.linkBankAccountBackBtnn);
        textView = findViewById(R.id.linkedBankNameTxt);
        linkedBankImg = findViewById(R.id.linkedBankImg);
        save = findViewById(R.id.saveAccBtn);
        routingTxt = findViewById(R.id.routingTxt);
        accountNumberTxt = findViewById(R.id.accountNumberTxt);
        service = Config.getInstance().create(BankService.class);


    }

    void setListeners(){
        back.setOnClickListener(backAction);
        save.setOnClickListener(saveAction);
    }

    void setValues(){
        String bankDetails = getIntent().getStringExtra("bankListName");
        bitmap = getIntent().getParcelableExtra("BitmapImage");

        textView.setText(bankDetails);
        bitmap = getIntent().getParcelableExtra("bankListImage");
        linkedBankImg.setImageBitmap(bitmap);
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
    View.OnClickListener backAction = new View.OnClickListener() {
        public void onClick(View v) {
            identifier = getIntent().getStringExtra("identifier");
            Intent mainIntent = new Intent(LinkBankAccountActivity.this,BankListActivity.class);
            mainIntent.putExtra("identifier", identifier);
            LinkBankAccountActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener saveAction = new View.OnClickListener() {
        public void onClick(View v) {

            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String userData = new LocalData().getlocalData(sharedPref, "userdata");
            int userid = 0;
            try {
                JSONObject jsonObj = new JSONObject(userData);
                userid = jsonObj.getInt("userid");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Call<JsonObject> saveBank = service.save(
                    new Bank(
                        Integer.parseInt(routingTxt.getText().toString()),
                        Integer.parseInt(accountNumberTxt.getText().toString()),
                            "John Doe's Savings Account",
                            userid
                    ));

            saveBank.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    String status = null;
                    try {
                        status = response.body().get("status").getAsString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){

                        // re-direct next form
                        vewAlert("Successfully","Press ok to continue",LinkBankAccountActivity.this);

                    }else{
                        System.out.println(status);
                        vewAlert("Warnings","Failed to save your account",LinkBankAccountActivity.this);

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    System.out.println("Error "+t.getMessage());
                }
            });
        }
    };


    public void vewAlert(final String title, String message, Context context){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // re-direct next form
                        if (title.equals("Successfully")){
                            Intent mainIntent = new Intent(LinkBankAccountActivity.this,HomePageActivity.class);
                            LinkBankAccountActivity.this.startActivity(mainIntent);
                        }
                    }
                });
        alertDialog.show();
    }

}
