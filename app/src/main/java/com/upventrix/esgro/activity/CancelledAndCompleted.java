package com.upventrix.esgro.activity;

import android.annotation.SuppressLint;
import android.app.MediaRouteButton;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.Deal;
import com.upventrix.esgro.modals.Dispute;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.LocalData;
import com.upventrix.esgro.services.DealService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelledAndCompleted extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    List<Dispute> disputeList;
    Button canceledBackBtn;
    private ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    DealService dealService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled_completed);

        idInitialization();
        setListeners();
        progressBar.setVisibility(View.VISIBLE);
        setValues();

    }

    void idInitialization(){

        canceledBackBtn = findViewById(R.id.canceledBackBtn);
        canceledBackBtn.setOnClickListener(backAction);
        dealService = Config.getInstance().create(DealService.class);

        disputeList = new ArrayList<>();
        initializeArray();

        ListView listView = findViewById(R.id.cancelledListView);
        progressBar = findViewById(R.id.canceledPageProgressBAr);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

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
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    void setValues(){

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userData = new LocalData().getlocalData(sharedPref, "userdata");
        int userid = 0;
        try {
            JSONObject jsonObj = new JSONObject(userData);
            userid = jsonObj.getInt("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<JsonObject> jsonObjectCall = dealService.doneDeal(""+userid);
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String status = "";
                try {
                    status = response.body().get("status").getAsString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (status.equals("success")){
                    JsonArray dealsList = response.body().getAsJsonArray("deals");

                    System.out.println("dealsList    ......  "+dealsList);

                    for (JsonElement value : dealsList) {

                        Dispute dispute = new Dispute();

                        dispute.setName(value.getAsJsonObject().get("firstname").getAsString() + " " + value.getAsJsonObject().get("lastname").getAsString());
                        dispute.setPrice(value.getAsJsonObject().get("total_cost").getAsDouble()+"");
                        dispute.setDays(value.getAsJsonObject().get("status").getAsString());
                        dispute.setDiscrption(value.getAsJsonObject().get("description").getAsString());
                        dispute.setId(value.getAsJsonObject().get("deal_id").getAsInt());
                        dispute.setReserve(value.getAsJsonObject().get("reserve_cost").getAsDouble()+"");

                        try {
                            dispute.setImage(value.getAsJsonObject().get("profileImgUrl").getAsString());
                        }catch (UnsupportedOperationException e){
                            dispute.setImage(null);

                        }
                        disputeList.add(
                                dispute
                        );
                    }
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    CancelledAndCompleted.CustomAdaper customAdaper = new CancelledAndCompleted.CustomAdaper();
                    ListView listView = findViewById(R.id.cancelledListView);
                    listView.setAdapter(customAdaper);
                 }else{
                    swipeRefreshLayout.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                    System.out.println(status);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);

            }
        });
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
    @Override
    public void onRefresh() {
        System.out.println("Refresh");
        disputeList.clear();
        setValues();
        swipeRefreshLayout.setRefreshing(false);
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
            DecimalFormat df = new DecimalFormat("####0.00");

            TextView bankNameView = convertView.findViewById(R.id.cancelledUserName);
            TextView disputePrice = convertView.findViewById(R.id.cancelledPrice);
            TextView disputedays = convertView.findViewById(R.id.cancelledStatus);
            TextView id = convertView.findViewById(R.id.idTxt);
            TextView description = convertView.findViewById(R.id.cancelledDescription);

            Dispute dispute = disputeList.get(position);

            bankNameView.setText(dispute.getName());
            disputedays.setText(dispute.getDays());
             description.setText(dispute.getDiscrption());
            id.setText(dispute.getId()+"");

            SimpleDraweeView simpleDraweeView = convertView.findViewById(R.id.userImg2);

             try {

                if (dispute.getImage().length()!=4){
                    Uri imageUri = Uri.parse(dispute.getImage());
                    simpleDraweeView.setController(
                            Fresco.newDraweeControllerBuilder()
                                    .setOldController(simpleDraweeView.getController())
                                    .setUri(imageUri)
                                    .setTapToRetryEnabled(true)
                                    .build());
                }else{
                    simpleDraweeView.setImageResource(R.drawable.roshen_kanishka);
                }

            }catch(Exception e){

            }

             disputePrice.setText("$"+dispute.getPrice());

            if(Double.parseDouble(dispute.getPrice())<0){
                StringBuilder sb = new StringBuilder(dispute.getPrice());
                sb.deleteCharAt(0);
                String format = df.format(Double.parseDouble(sb.toString()));
                disputePrice.setText("-$"+format);
             }
            if(Double.parseDouble(dispute.getPrice())>0){
                String format = df.format(Double.parseDouble(dispute.getPrice()));
                disputePrice.setText("+$"+format);

            }
            if (dispute.getDays().equals("cancelled")|| dispute.getDays().equals("completed")){
                disputePrice.setTextColor(Color.parseColor("#929AAB"));
                convertView.setBackgroundResource(R.drawable.layout_low_gray_corner);
                disputedays.setTextColor(Color.parseColor("#929AAB"));

            }

            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
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
