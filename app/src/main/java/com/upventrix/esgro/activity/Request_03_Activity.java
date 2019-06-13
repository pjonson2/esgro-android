package com.upventrix.esgro.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rafaelbarbosatec.archivimentview.AchievementView;
import com.rafaelbarbosatec.archivimentview.iterface.ShowListern;
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.Deal;
import com.upventrix.esgro.modals.User;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.LocalData;
import com.upventrix.esgro.services.DealService;
import com.upventrix.esgro.services.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Request_03_Activity extends AppCompatActivity {

    Button back;
    Button continues;

    Dialog dialog;

    private final int SPLASH_DISPLAY_LENGTH = 4000;
    private final int SPLASH_DISPLAY_LENGTH_1 = 2000;

    EditText userName;
    EditText days;
    EditText amount;
    EditText reserve;
    EditText description;

    Bundle extras;

    String charging_amount;
    String holding_days;
    String reservePrice;
    String userId;

    DealService service;

    ConstraintLayout constraintLayout;
    AchievementView achievementView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_request_3);
        extras = getIntent().getExtras();

        idInitialization();
        setListeners();
        setValues();

        dialog = new Dialog(this);

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

    void idInitialization() {
        back = findViewById(R.id.request3Back);
        continues = findViewById(R.id.request3SendBtn);
        constraintLayout = findViewById(R.id.activity_request_3);
        achievementView = findViewById(R.id.achievementView);
        userName = findViewById(R.id.requestToUserNameTxt);
        days = findViewById(R.id.requestDaysTxt);
        amount = findViewById(R.id.requestAmountTxt);
        reserve = findViewById(R.id.requestReserveTxt);
        description = findViewById(R.id.requestDescriptionTxt);
        service = Config.getInstance().create(DealService.class);
    }

    void setListeners() {
        back.setOnClickListener(requestBack);
        continues.setOnClickListener(requestContinue);
        userName.setOnClickListener(goToSelectAgain);
        userName.setText(extras.getString("request_user"));
    }

    void setValues() {
        holding_days = extras.getString("holding_days");
        charging_amount = extras.getString("charging_amount");
        reservePrice = extras.getString("reserve_amount");
        userId = extras.getString("request_userId");
        amount.setText(charging_amount);
        days.setText(holding_days);
        reserve.setText(reservePrice);

        System.out.println("User Id is a  " + userId);
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

    View.OnClickListener requestBack = new View.OnClickListener() {
        public void onClick(View v) {

            String days = extras.getString("holding_days");
            String prices = extras.getString("charging_amount");
            String rePrice = extras.getString("reserve_amount");
            String userId = extras.getString("request_userId");

            Intent mainIntent = new Intent(Request_03_Activity.this, Request_02_Activity.class);
            mainIntent.putExtra("holding_days", days);
            mainIntent.putExtra("charging_amount", prices);
            mainIntent.putExtra("reserve_amount", rePrice);
            mainIntent.putExtra("request_userId", userId);
            mainIntent.putExtra("request_user", userName.getText().toString());

            Request_03_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener goToSelectAgain = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(Request_03_Activity.this, RequestActivity.class);
            Request_03_Activity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener requestContinue = new View.OnClickListener() {
        public void onClick(View v) {
            continues.setEnabled(false);
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String userData = new LocalData().getlocalData(sharedPref, "userdata") + "";
            int userid = 0;
            try {
                JSONObject jsonObj = new JSONObject(userData);
                userid = jsonObj.getInt("user_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(description.getText().toString().equals("")){
                continues.setEnabled(true);
                new  ToastActivity().showFailed(
                        achievementView,
                        "Warnings!",
                        "Fill Description!"
                );
                return;
            }

            StringBuilder chargePriceBuilder = new StringBuilder(charging_amount);
            chargePriceBuilder.deleteCharAt(0);

            StringBuilder reservePriceBuilder = new StringBuilder(reservePrice);
            reservePriceBuilder.deleteCharAt(0);


            Call<JsonObject> userCall = service.saveDEal(
                    new Deal(
                            description.getText().toString(),
                            Double.parseDouble(chargePriceBuilder.toString()),
                            Double.parseDouble(reservePriceBuilder.toString()),
                            days.getText().toString(),
                            userid,
                            Integer.parseInt(userId)
                    ));

            userCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    System.out.println("response.body().  "+response.body());
                    String status = "";
                    try {
                        status = response.body().get("status").getAsString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")) {

                        showOK(achievementView,"Successfully","Your deal successfully saved");

                    } else {
                        continues.setEnabled(true);
                        new  ToastActivity().showFailed(
                                achievementView,
                                "Warnings!",
                                "Your deal saving failed"
                        );
                     }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    System.out.println("Error " + t.getMessage());
                    continues.setEnabled(true);
                }
            });
        }
    };

//        public void vewAlert(final String title, String message, final Context context) {
//            final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//            alertDialog.setTitle(title);
//            alertDialog.setMessage(message);
//            alertDialog.setCanceledOnTouchOutside(false);
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                    (dialog1, which) -> {
//                        dialog1.dismiss();
//                        if (title.equals("Successfully")) {
//
//                            dialog.setContentView(R.layout.activity_proessing_alert);
//                            dialog.setCanceledOnTouchOutside(false);
//                            final StringBuilder builder = new StringBuilder(charging_amount);
//                            builder.replace(0,1,"");
//
//                            dialog.show();
//                            Window window = dialog.getWindow();
//
//                            final TextView weLbl = window.findViewById(R.id.weLbl);
//                            final TextView esgroLbl = window.findViewById(R.id.esgroLbl);
//                            final TextView themLbl = window.findViewById(R.id.themLbl);
//                            esgroLbl.setTextColor(Color.parseColor("#929AAB"));
//                            weLbl.setTextColor(Color.parseColor("#7FE239"));
//
//                            weLbl.setText(charging_amount);
//                            esgroLbl.setText(charging_amount);
//                            themLbl.setText(charging_amount);
//
//                            new Handler().postDelayed(() -> {
//
//                                if(Double.parseDouble(builder.toString())>0){
//                                    weLbl.setTextColor(Color.parseColor("#929AAB"));
//                                    esgroLbl.setTextColor(Color.parseColor("#7FE239"));
//                                }
//                            }, SPLASH_DISPLAY_LENGTH_1);
//
//                            dialog.show();
//                            new Handler().postDelayed(() -> {
//                                dialog.dismiss();
//
//                                Intent mainIntent = new Intent(Request_03_Activity.this,DisputeNoHistoryActivity.class);
//                                Request_03_Activity.this.startActivity(mainIntent);
//                            }, SPLASH_DISPLAY_LENGTH);
//
//                        }
//                        });
//            alertDialog.show();
//        }

    public void showOK(final AchievementView achievementView, String title, String body){

        achievementView
                .setTitle(title)
                .setMensage(body)
                //.setBorderRetangle()
                .setColor(R.color.colorAccent)
                .setIcon(R.drawable.checked_50px)
                //.setScaleTypeIcon(ImageView.ScaleType.CENTER_INSIDE)
                .setDuration(3000) // or time in milliseconds

                .setShowListern(new ShowListern() {
                    @Override
                    public void start() {
                        Log.i("LOG","start");
                    }

                    @Override
                    public void show() {
                        Log.i("LOG","show");
                    }

                    @Override
                    public void dimiss() {
                        Log.i("LOG","dismiss");

                        dialog.setContentView(R.layout.activity_proessing_alert);
                        dialog.setCanceledOnTouchOutside(false);
                        final StringBuilder builder = new StringBuilder(charging_amount);
                        builder.replace(0,1,"");

                        dialog.show();
                        Window window = dialog.getWindow();

                        final TextView weLbl = window.findViewById(R.id.weLbl);
                        final TextView esgroLbl = window.findViewById(R.id.esgroLbl);
                        final TextView themLbl = window.findViewById(R.id.themLbl);
                        esgroLbl.setTextColor(Color.parseColor("#929AAB"));
                        weLbl.setTextColor(Color.parseColor("#7FE239"));

                        weLbl.setText(charging_amount);
                        esgroLbl.setText(charging_amount);
                        themLbl.setText(charging_amount);

                        new Handler().postDelayed(() -> {

                            if(Double.parseDouble(builder.toString())>0){
                                weLbl.setTextColor(Color.parseColor("#929AAB"));
                                esgroLbl.setTextColor(Color.parseColor("#7FE239"));
                            }
                        }, SPLASH_DISPLAY_LENGTH_1);

                        dialog.show();
                        new Handler().postDelayed(() -> {
                            dialog.dismiss();

                            Intent mainIntent = new Intent(Request_03_Activity.this,DisputeNoHistoryActivity.class);
                            Request_03_Activity.this.startActivity(mainIntent);
                        }, SPLASH_DISPLAY_LENGTH);

                    }

                    @Override
                    public void end() {
                        Log.i("LOG","end");
                    }
                })
                .show();
    }

}