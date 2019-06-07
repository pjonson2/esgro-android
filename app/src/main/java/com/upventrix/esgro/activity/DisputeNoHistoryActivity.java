package com.upventrix.esgro.activity;

import android.Manifest;
import android.app.Activity;
import android.app.admin.DeviceAdminInfo;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.Dispute;
import com.upventrix.esgro.modals.UserToken;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.LocalData;
import com.upventrix.esgro.services.DealService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.upventrix.esgro.services.NotificationService;
import com.upventrix.esgro.services.UserService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisputeNoHistoryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    List<Dispute> disputeList;
    EditText disputeShakeSearchView;

    ProgressBar progressBar;

    ImageView contactIcon;
    ImageView profileIcon;
    ImageView newPostIcon;
    ImageView settings;
    DealService service = null;
    UserService userService = null;
    SwipeRefreshLayout swipeRefreshLayout;

    ConstraintLayout constraintLayout;

    private String deviceName = "";

    private static Socket mSocket;

    private NotificationService notificationService;

    String message = "";
    int userid = 0;

    {
        try {
            mSocket = IO.socket("https://esgro-api.herokuapp.com");
        } catch (URISyntaxException e) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispute_shake);


        idInitialization();
        setListeners();
        setToken();


        String model = Build.MODEL;
        String brand = Build.BRAND;
        String device = Build.DEVICE;
        deviceName = brand+" "+device+" "+model;
        System.out.println("deviceName  "+deviceName);

        mSocket.on("deal_result", onDealResult);
        mSocket.connect();

        constraintLayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });
    }


    private void attemptSend() {
        String searchTxt = disputeShakeSearchView.getText().toString();
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userData = new LocalData().getlocalData(sharedPref, "userdata");
        int userid = 0;
        try {
            JSONObject jsonObj = new JSONObject(userData);
            userid = jsonObj.getInt("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String payload = "{ \"user_id\": \"" + userid + "\", \"term\": \"" + searchTxt + "\" }";

        if (searchTxt.length() > 3) {
            System.out.println("message isEmpty ");
            return;
        }

        mSocket.emit("deal_search", payload);
    }

    private Emitter.Listener onDealResult = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            DisputeNoHistoryActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        JSONObject data = (JSONObject) args[0];
                        String  jsonObject =   data.get("status").toString();

                        if (jsonObject.toString().equals("success")){
                            disputeList.clear();
                        JSONArray deals = data.getJSONArray("deals");

                        if (deals.length() == 0){
                            String text = "No Result!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(DisputeNoHistoryActivity.this, text, duration);
                            toast.show();
                        }
                            disputeList.clear();
                        for (int i = 0; i < deals.length(); ++i) {
                            JSONObject rec = deals.getJSONObject(i);
                            Dispute dispute = new Dispute();

                                dispute.setName(rec.getString("firstname") + " " + rec.getString("lastname"));
                                dispute.setPrice(rec.getDouble("total_cost")+"");
                                dispute.setDays(rec.getString("status"));
                                dispute.setDiscrption(rec.getString("description"));
                                dispute.setId(rec.getInt("deal_id"));
                                dispute.setReserve(rec.getDouble("reserve_cost")+"");
                        try {
                            dispute.setImage(rec.getString("profileImgUrl")+"");
                        }catch (UnsupportedOperationException e){
                            dispute.setImage(null);
                        }
                            disputeList.add(
                                        dispute
                                );
                        }
                            progressBar.setVisibility(View.GONE);
                            swipeRefreshLayout.setRefreshing(false);
                            DisputeNoHistoryActivity.CustomAdaper customAdaper = new DisputeNoHistoryActivity.CustomAdaper();
                            ListView listView = findViewById(R.id.dynamicShakeListView);
                            listView.setAdapter(customAdaper);
                            progressBar.setVisibility(View.GONE);

                        }else{
                            setValues();
                            swipeRefreshLayout.setRefreshing(false);
                            progressBar.setVisibility(View.GONE);
                            System.out.println(jsonObject);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ClassCastException e){
                        disputeList.clear();
                        setValues();
                    }

                }
            });
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        System.out.println("Socket disconnecting ..... ");
     }


    private void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }



    private void setToken() {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String device_id ="";
        String notification_token = new LocalData().getlocalData(sharedPref, "device_token");
        String userData = new LocalData().getlocalData(sharedPref, "userdata");
        try {
            JSONObject jsonObj = new JSONObject(userData);
            userid = jsonObj.getInt("user_id");
            device_id = new LocalData().getlocalData(sharedPref, "device_id")+"";
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("device_id   "+device_id);
        System.out.println("device_token   "+notification_token);

        if(device_id.length() == 4){
            System.out.println("Device Id NULL "+device_id);
            return;
        }

        Call<JsonObject> token = notificationService.getToken(device_id);
        token.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String status = "";
                try {
                    status = response.body().get("status").getAsString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (status.equals("success")){
                    JsonObject userData = response.body().getAsJsonObject("data");
                    final String device_token = userData.get("device_token").getAsString();
                    final String apiUserId = userData.get("user_id").toString();
                    System.out.println("notification_token "+notification_token);
                    System.out.println("device_token "+device_token);


                    if(!notification_token.equals(device_token) || !apiUserId.equals((""+userid))){
                        System.out.println("Running If");
                        new LocalData().setNotificationToken(sharedPref,device_token);
                    }

                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
//
    }

    void idInitialization(){
        disputeShakeSearchView = findViewById(R.id.disputeShakeSearchView);
        contactIcon = findViewById(R.id.disputesContactIcon);
        profileIcon = findViewById(R.id.disputesProfileIcon);
        newPostIcon = findViewById(R.id.disputesNewPostIcon);
        settings = findViewById(R.id.disputesSettingsIcon);
        progressBar = findViewById(R.id.progressBar2);
        constraintLayout = findViewById(R.id.activity_dispute_shake);
        swipeRefreshLayout =  findViewById(R.id.swipeRefreshLayout);
        service = Config.getInstance().create(DealService.class);
        userService = Config.getInstance().create(UserService.class);
        notificationService = Config.getInstance().create(NotificationService.class);

        disputeList = new ArrayList<>();
        setValues();

        ListView listView = findViewById(R.id.dynamicShakeListView);
        listView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                TextView textView = view.findViewById(R.id.disputeUserName);
                String name = textView.getText().toString();

                SimpleDraweeView imageView = view.findViewById(R.id.userImg);
                imageView.buildDrawingCache();

                TextView disputePriceTxt = view.findViewById(R.id.disputePriceTxt);
                String disputePrice = disputePriceTxt.getText().toString();

                TextView disputeDaysTxt = view.findViewById(R.id.disputeDaysTxt);
                String disputeDays = disputeDaysTxt.getText().toString();

                TextView disputeDescriptionTxt = view.findViewById(R.id.disputeDiscription);
                String disputeDescription = disputeDescriptionTxt.getText().toString();

                TextView deal_id = view.findViewById(R.id.dealID);
                String dealID = deal_id.getText().toString();

                TextView reservePriceTxt = view.findViewById(R.id.reservePriceTxt);
                String reservePrice = reservePriceTxt.getText().toString();

                Bitmap bitmap = imageView.getDrawingCache();

                Intent intent = new Intent(DisputeNoHistoryActivity.this, DisputeDetails_No_History_Activity.class);

                intent.putExtra("disputeListName", name);
                intent.putExtra("BitmapImage", bitmap);
                intent.putExtra("disputeListPrice", disputePrice);
                intent.putExtra("disputeListDays", disputeDays);
                intent.putExtra("disputeListDescription", disputeDescription);
                intent.putExtra("deal_id",dealID);
                intent.putExtra("reserve_cost",reservePrice);

                startActivity(intent);

            }
        });

    }

    void setListeners(){
        settings.setOnClickListener(home);
        contactIcon.setOnClickListener(contactUs);
        profileIcon.setOnClickListener(profile);
        newPostIcon.setOnClickListener(newAction);
        disputeShakeSearchView.addTextChangedListener(n1Change);
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

                    System.out.println("dealsList    ......  "+dealsList);
                    disputeList.clear();
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
                        DisputeNoHistoryActivity.CustomAdaper customAdaper = new DisputeNoHistoryActivity.CustomAdaper();
                    ListView listView = findViewById(R.id.dynamicShakeListView);
                    listView.setAdapter(customAdaper);
                    progressBar.setVisibility(View.GONE);

                }else{
                    swipeRefreshLayout.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                    System.out.println(status);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("Error "+t.getMessage());
            }
        });
    }

    public void setHitArea(){
            {
                View parent = (View) settings.getParent();  // button: the view you want to enlarge hit area
                parent.post(new Runnable() {
                    public   void run() {
                        Rect rect = new Rect();
                        settings.getHitRect(rect);
                        rect.top -= 80;    // increase top hit area
                        rect.left -= 80;   // increase left hit area
                        rect.bottom += 80; // increase bottom hit area
                        rect.right += 80;  // increase right hit area
                        parent.setTouchDelegate(new TouchDelegate(rect, settings));
                        System.out.println("Thread 1");
                    }
                });
            }  {
                View parent = (View) profileIcon.getParent();  // button: the view you want to enlarge hit area
                parent.post(new Runnable() {
                    public   void run() {
                        Rect rect = new Rect();
                        profileIcon.getHitRect(rect);
                        rect.top -= 80;    // increase top hit area
                        rect.left -= 80;   // increase left hit area
                        rect.bottom += 80; // increase bottom hit area
                        rect.right += 80;  // increase right hit area
                        profileIcon.setTouchDelegate(new TouchDelegate(rect, profileIcon));
                        System.out.println("Thread 2");

                    }
                });
            }  {
                View parent = (View) contactIcon.getParent();  // button: the view you want to enlarge hit area
                parent.post(new Runnable() {
                    public   void run() {
                        Rect rect = new Rect();
                        contactIcon.getHitRect(rect);
                        rect.top -= 80;    // increase top hit area
                        rect.left -= 80;   // increase left hit area
                        rect.bottom += 80; // increase bottom hit area
                        rect.right += 80;  // increase right hit area
                        contactIcon.setTouchDelegate(new TouchDelegate(rect, contactIcon));

                    }
                });
            }

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
            DecimalFormat df = new DecimalFormat("####0.00");

            TextView bankNameView = convertView.findViewById(R.id.disputeUserName);

            TextView disputePrice = convertView.findViewById(R.id.disputePriceTxt);
            TextView disputedays = convertView.findViewById(R.id.disputeDaysTxt);
            TextView disputeDesc = convertView.findViewById(R.id.disputeDiscription);
            TextView dealID = convertView.findViewById(R.id.dealID);
            TextView reservePriceTxt = convertView.findViewById(R.id.reservePriceTxt);

            SimpleDraweeView simpleDraweeView = convertView.findViewById(R.id.userImg);

            Dispute dispute = disputeList.get(position);
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
            bankNameView.setText(dispute.getName());
            disputedays.setText(dispute.getDays());
            disputePrice.setText("$"+dispute.getPrice());
            disputeDesc.setText(dispute.getDiscrption());
            dealID.setText(dispute.getId()+"");
            reservePriceTxt.setText("$"+dispute.getReserve());

            StringBuilder reserveSb = new StringBuilder(reservePriceTxt.getText());
            reserveSb.deleteCharAt(0);
            String serverFormat = df.format(Double.parseDouble(reserveSb.toString()));
            reservePriceTxt.setText("$"+serverFormat);

            if(Double.parseDouble(dispute.getPrice())<0){
                StringBuilder sb = new StringBuilder(dispute.getPrice());
                sb.deleteCharAt(0);
                String format = df.format(Double.parseDouble(sb.toString()));
                disputePrice.setText("-$"+format);
                disputePrice.setTextColor(Color.RED);
            }
            if(Double.parseDouble(dispute.getPrice())>0){
                 String format = df.format(Double.parseDouble(dispute.getPrice()));
                 disputePrice.setText("+$"+format);
                disputePrice.setTextColor(getResources().getColor(R.color.lightGreen));

            }
            if (dispute.getDays().equals("cancelled")|| dispute.getDays().equals("completed")){
                disputePrice.setTextColor(Color.parseColor("#929AAB"));
                convertView.setBackgroundResource(R.drawable.layout_low_gray_corner);
                disputedays.setTextColor(Color.parseColor("#929AAB"));

            }
            if(dispute.getDays().equals("waiting")){
                disputePrice.setTextColor(Color.parseColor("#929AAB"));
                disputedays.setTextColor(Color.parseColor("#929AAB"));
            }
            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
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

//    class MyTask extends AsyncTask<Void , Void, Void>{
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try {
//                System.out.println("doInBackground Executing .....");
//                socket = new Socket(ip,5000);
//                System.out.println("doInBackground socket ....."+socket);
//                printWriter = new PrintWriter(socket.getOutputStream());
//                printWriter.write(message);
//                printWriter.flush();
//                System.out.println("doInBackground  printWriter ....   "+printWriter);
//                printWriter.close();
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//    }
    TextWatcher n1Change = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            System.out.println("beforeTextChanged");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            System.out.println("onTextChanged");
        }

        @Override
        public void afterTextChanged(Editable s) {
            System.out.println("afterTextChanged");
            message = disputeShakeSearchView.getText().toString();
//            System.out.println("DATA SENT "+message);
            attemptSend();
//            MyTask myTask = new MyTask();
//            myTask.execute();
        }
    };

}
