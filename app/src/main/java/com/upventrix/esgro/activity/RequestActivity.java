package com.upventrix.esgro.activity;

import android.content.Intent;
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
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.Request;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.services.UserService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestActivity extends AppCompatActivity {

    Button back;

    List<Request> requestList;
    private UserService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        idInitialization();
        setListeners();

    }
    void idInitialization(){
        service = Config.getInstance().create(UserService.class);
        requestList = new ArrayList<>();
        setValues();
        back = findViewById(R.id.requestBackBtn);
        ListView listView = findViewById(R.id.dynamicRequestList);


        RequestActivity.CustomAdaper customAdaper = new RequestActivity.CustomAdaper();
        listView.setAdapter(customAdaper);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                TextView textView = view.findViewById(R.id.requestUserName);
                TextView useridTxt = view.findViewById(R.id.userIdTxt);

                String name = textView.getText().toString();
                String userId = useridTxt.getText().toString();

                Intent intent = new Intent(RequestActivity.this, Request_01_Activity.class);
                intent.putExtra("request_user",name);
                intent.putExtra("request_userId",userId);
                startActivity(intent);
            }
        });

    }

    void setListeners(){
        back.setOnClickListener(backFromRequest);
    }

    void setValues(){
        Call<JsonObject> userCall = service.usersList();
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

                    JsonArray userslist = response.body().getAsJsonArray("userslist");

                    for (JsonElement value : userslist) {
                        requestList.add(
                            new Request(
                                value.getAsJsonObject().get("userid").getAsInt(),
                                value.getAsJsonObject().get("firstname").getAsString()+" "+value.getAsJsonObject().get("lastname").getAsString(),
                                "3 days ago",
                                R.drawable.user1
                            )
                        );
                        System.out.println(value.getAsJsonObject().get("userid").getAsInt());
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

    View.OnClickListener backFromRequest = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(RequestActivity.this, DisputeNoHistoryActivity.class);
            RequestActivity.this.startActivity(mainIntent);
        }
    };

    class CustomAdaper extends BaseAdapter {

        @Override
        public int getCount() {
            return requestList.size();
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
            convertView = getLayoutInflater().inflate(R.layout.activity_request_card,null);

            TextView bankNameView = convertView.findViewById(R.id.requestUserName);
            ImageView requestImg = convertView.findViewById(R.id.requestUserImg);
            TextView daysTxt = convertView.findViewById(R.id.requestDays);
            TextView userIdTxt = convertView.findViewById(R.id.userIdTxt);

            Request request = requestList.get(position);
            System.out.println("request.getUserid()  "+request.getUserid());
            requestImg.setImageResource(request.getImage());
            bankNameView.setText(request.getName());
            daysTxt.setText(request.getDays());
            userIdTxt.setText(request.getUserid()+"");
            return convertView;
        }
    }

}




