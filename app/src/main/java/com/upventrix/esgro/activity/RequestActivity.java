package com.upventrix.esgro.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
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
    ProgressBar progressBar;

    List<Request> requestList;
    private UserService service;

    EditText searchView;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        constraintLayout = findViewById(R.id.activity_request);

        idInitialization();
        searchView.requestFocus();
        setListeners();
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

    private void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    void idInitialization(){
        searchView = findViewById(R.id.searchView);
        progressBar = findViewById(R.id.progressBar);
        service = Config.getInstance().create(UserService.class);
        requestList = new ArrayList<>();
        setValues();
        back = findViewById(R.id.requestBackBtn);

        ListView listView = findViewById(R.id.dynamicRequestList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                System.out.println("onItemClick()");

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
                        Request request = new Request();
                        request.setDays("3 days ago");
                        request.setName(  value.getAsJsonObject().get("firstname").getAsString()+" "+value.getAsJsonObject().get("lastname").getAsString());
                        request.setUserid(value.getAsJsonObject().get("user_id").getAsInt());

                        try {
                            request.setImage(value.getAsJsonObject().get("profileImgUrl").getAsString());
                        }catch (UnsupportedOperationException e){
                            request.setImage(null);
                        }

                        requestList.add(
                            request
                        );
                     }
                     ListView listView = findViewById(R.id.dynamicRequestList);

                    RequestActivity.CustomAdaper customAdaper = new RequestActivity.CustomAdaper();
                    listView.setAdapter(customAdaper);
                    progressBar.setVisibility(View.GONE);

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
            SimpleDraweeView requestImg = convertView.findViewById(R.id.requestUserImg);
            TextView daysTxt = convertView.findViewById(R.id.requestDays);
            TextView userIdTxt = convertView.findViewById(R.id.userIdTxt);

            Request request = requestList.get(position);
             try {

                if (request.getImage().length()!=4){
                    Uri imageUri = Uri.parse(request.getImage());
                    requestImg.setController(
                            Fresco.newDraweeControllerBuilder()
                                    .setOldController(requestImg.getController())
                                    .setUri(imageUri)
                                    .setTapToRetryEnabled(true)
                                    .build());
                }else{
                    requestImg.setImageResource(R.drawable.roshen_kanishka);
                }

            }catch(Exception e){

            }
            bankNameView.setText(request.getName());
            daysTxt.setText(request.getDays());
            userIdTxt.setText(request.getUserid()+"");
            return convertView;
        }
    }

}




