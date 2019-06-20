package com.upventrix.esgro.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.Bank;
import com.upventrix.esgro.modals.CardDetails;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.LocalData;
import com.upventrix.esgro.services.CardService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferToCardActivity  extends AppCompatActivity {

    Button back;
    Button transferBtn;

    Dialog dialog;


    View toAddCardView;
    private CardService cardService;
    List<CardDetails>cardDetailsList;
    ListView listView;
    ConstraintLayout constraintLayout;
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

        cardService = Config.getInstance().create(CardService.class);
        constraintLayout = findViewById(R.id.emptyView);
        cardDetailsList = new ArrayList<>();

        dialog = new Dialog(this);
        listView = findViewById(R.id.transferToCardList);

        TransferToCardActivity.CustomAdaper customAdaper = new TransferToCardActivity.CustomAdaper();
        listView.setAdapter(customAdaper);

        setValues();

    }

    private void setValues() {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userData = new LocalData().getlocalData(sharedPref, "userdata");
        int userid = 0;
        try {
            JSONObject jsonObj = new JSONObject(userData);
            userid = jsonObj.getInt("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<JsonObject> all = cardService.getAll(userid+"");
        all.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                boolean status = false;
                try {
                    status = response.body().get("status").getAsBoolean();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (status) {

                    JsonArray cardsList = response.body().getAsJsonArray("cards");

                    cardDetailsList.clear();

                     if (cardsList.size()==0){
                        return;
                    }
                    for (JsonElement value :cardsList) {

                        CardDetails cardDetails = new CardDetails();
                        cardDetails.setId(value.getAsJsonObject().get("id").getAsString());
                        cardDetails.setBrand(value.getAsJsonObject().get("brand").getAsString());
                        cardDetails.setExp_month(value.getAsJsonObject().get("exp_month").getAsInt());
                        cardDetails.setExp_year(value.getAsJsonObject().get("exp_year").getAsInt());
                        cardDetails.setLast_digits(value.getAsJsonObject().get("last_digits").getAsInt());
                        cardDetails.setCard_icon(value.getAsJsonObject().get("card_icon").getAsString());
                        cardDetailsList.add(cardDetails);
                    }
                    TransferToCardActivity.CustomAdaper customAdaper = new TransferToCardActivity.CustomAdaper();
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


    class CustomAdaper extends BaseAdapter {

        @Override
        public int getCount() {
            return cardDetailsList.size();
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
            String httpUrl = Config.getInstance().baseUrl().toString();

            SimpleDraweeView simpleDraweeView = convertView.findViewById(R.id.bankImage);
            CardDetails card = cardDetailsList.get(position);
            Uri imageUri = Uri.parse(httpUrl+"card-icons/"+card.getCard_icon());
                    simpleDraweeView.setController(
                            Fresco.newDraweeControllerBuilder()
                                    .setOldController(simpleDraweeView.getController())
                                    .setUri(imageUri)
                                    .setTapToRetryEnabled(true)
                                    .build());
            bankNameView.setText(card.getExp_month()+" / "+card.getExp_year());
            bankDetails.setText("Card no. ends in "+card.getLast_digits());
            return convertView;
        }
    }
}

