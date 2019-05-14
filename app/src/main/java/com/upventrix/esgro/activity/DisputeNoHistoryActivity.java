package com.upventrix.esgro.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.Dispute;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.LocalData;
import com.upventrix.esgro.services.DealService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisputeNoHistoryActivity extends AppCompatActivity {

    List<Dispute> disputeList;
    EditText disputeShakeSearchView;

    ImageView contactIcon;
    ImageView profileIcon;
    ImageView newPostIcon;
    ImageView settings;
    DealService service = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispute_shake);
        hideKeyboard(this);
        idInitialization();
        setListeners();

    }

    void idInitialization(){
        disputeShakeSearchView = findViewById(R.id.disputeShakeSearchView);
        contactIcon = findViewById(R.id.disputesContactIcon);
        profileIcon = findViewById(R.id.disputesProfileIcon);
        newPostIcon = findViewById(R.id.disputesNewPostIcon);
        settings = findViewById(R.id.disputesSettingsIcon);
        service = Config.getInstance().create(DealService.class);

        disputeList = new ArrayList<>();
        setValues();

        ListView listView = findViewById(R.id.dynamicShakeListView);

        DisputeNoHistoryActivity.CustomAdaper customAdaper = new DisputeNoHistoryActivity.CustomAdaper();
        listView.setAdapter(customAdaper);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                TextView textView = view.findViewById(R.id.disputeUserName);
                String name = textView.getText().toString();

                ImageView imageView = view.findViewById(R.id.disputeUserImage);
                imageView.buildDrawingCache();

                TextView disputePriceTxt = view.findViewById(R.id.disputePriceTxt);
                String disputePrice = disputePriceTxt.getText().toString();

                TextView disputeDaysTxt = view.findViewById(R.id.disputeDaysTxt);
                String disputeDays = disputeDaysTxt.getText().toString();

                Bitmap bitmap = imageView.getDrawingCache();

                Intent intent = new Intent(DisputeNoHistoryActivity.this, DisputeDetails_No_History_Activity.class);

                intent.putExtra("disputeListName", name);
                intent.putExtra("BitmapImage", bitmap);
                intent.putExtra("disputeListPrice", disputePrice);
                intent.putExtra("disputeListDays", disputeDays);

                startActivity(intent);

            }
        });

    }

    void setListeners(){
        settings.setOnClickListener(home);
        contactIcon.setOnClickListener(contactUs);
        profileIcon.setOnClickListener(profile);
        newPostIcon.setOnClickListener(newAction);
    }

    void setValues(){

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userData = new LocalData().getlocalData(sharedPref, "userdata");
        int userid = 0;
        try {
            JSONObject jsonObj = new JSONObject(userData);
            userid = jsonObj.getInt("userid");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Call<JsonObject> userCall = service.dealAll(""+userid);
          userCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String status = null;
                try {
                    status = response.body().get("status").getAsString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (status.equals("success")){

                    JsonArray dealsList = response.body().getAsJsonArray("deals");

                        for (JsonElement value : dealsList) {
                        disputeList.add(
                                new Dispute(
                                        value.getAsJsonObject().get("firstname").getAsString()+" "+value.getAsJsonObject().get("lastname").getAsString(),
                                        value.getAsJsonObject().get("description").getAsString(),
                                        value.getAsJsonObject().get("total_cost").getAsDouble()+"",
                                        value.getAsJsonObject().get("status").getAsString(),
                                        R.drawable.user1
                                )
                        );
                    }

                }else{
                    System.out.println(status);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("Error "+t.getMessage());
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.activity_dispute_card,null);

            TextView bankNameView = convertView.findViewById(R.id.disputeUserName);

            TextView disputePrice = convertView.findViewById(R.id.disputePriceTxt);
            TextView disputedays = convertView.findViewById(R.id.disputeDaysTxt);
            TextView disputeDesc = convertView.findViewById(R.id.disputeDiscription);
            ImageView deisputeImg = convertView.findViewById(R.id.disputeUserImage);

            Dispute dispute = disputeList.get(position);
            deisputeImg.setImageResource(dispute.getImage());
            bankNameView.setText(dispute.getName());
            disputedays.setText(dispute.getDays());
            disputePrice.setText(dispute.getPrice());
            disputeDesc.setText(dispute.getDiscrption());


            if(Double.parseDouble(dispute.getPrice())<0){
                disputePrice.setTextColor(Color.RED);
            }
            if(Double.parseDouble(dispute.getPrice())>0){
                disputePrice.setTextColor(getResources().getColor(R.color.lightGreen));
            }
            if (dispute.getDays().equals("Canceled")|| dispute.getDays().equals("Completed")){
                disputePrice.setTextColor(Color.parseColor("#929AAB"));
                convertView.setBackgroundResource(R.drawable.layout_low_gray_corner);
                disputedays.setTextColor(Color.parseColor("#929AAB"));

            }
            if(dispute.getDays().equals("waiting")){
                disputePrice.setTextColor(Color.parseColor("#929AAB"));
                disputedays.setTextColor(Color.parseColor("#929AAB"));
            }
            return convertView;
        }
    }


    View.OnClickListener contactUs = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeNoHistoryActivity.this,ContactUsActivity.class);
            DisputeNoHistoryActivity.this.startActivity(mainIntent);
        }
    };

    View.OnClickListener profile = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeNoHistoryActivity.this,ProfileActivity.class);
            DisputeNoHistoryActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener home = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeNoHistoryActivity.this,HomePageActivity.class);
            DisputeNoHistoryActivity.this.startActivity(mainIntent);
        }
    };

    View.OnClickListener newAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(DisputeNoHistoryActivity.this,RequestActivity.class);
            DisputeNoHistoryActivity.this.startActivity(mainIntent);
        }
    };

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

}
