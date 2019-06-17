package com.upventrix.esgro.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.hbb20.CountryCodePicker;
import com.rafaelbarbosatec.archivimentview.AchievementView;
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.User;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.LocalData;
import com.upventrix.esgro.services.UserService;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileVerificationActivity  extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button backBtn;
    Button nextBtn;

    Spinner spinner;

    EditText mobileCerificNumber;
    String selectedCountryCode = "";
    UserService service = null;

    RelativeLayout relativeLayout;
    CountryCodePicker ccp;
    AchievementView achievementView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);

        idInitialization();
        setListeners();
        setValues();

//        loadSpinner();
        relativeLayout = findViewById(R.id.activity_mobile_verification);
        relativeLayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                selectedCountryCode = ccp.getSelectedCountryCode();
                System.out.println(" selectedCountryCode  "+selectedCountryCode);
            }
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    void idInitialization(){
        backBtn = findViewById(R.id.verificationBackBtn);
        nextBtn = findViewById(R.id.mobileVerificNxtBtn);
        spinner = findViewById(R.id.mobileVerificSpinner);
        mobileCerificNumber = findViewById(R.id.mobileCerificNumber);
        ccp = findViewById(R.id.ccp);
        achievementView = findViewById(R.id.achievementView);
        service = Config.getInstance().create(UserService.class);
    }

    void setListeners(){
        backBtn.setOnClickListener(backAction);
        nextBtn.setOnClickListener(nxtAction);

    }


    void setValues(){

    }

    void loadSpinner(){

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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
            Intent mainIntent = new Intent(MobileVerificationActivity.this,SignUpActivity.class);
            MobileVerificationActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener nxtAction = new View.OnClickListener() {
        public void onClick(View v) {

            if (selectedCountryCode.equals("")){
                new ToastActivity().showFailed(
                        achievementView,
                        "Warnings!",
                        "Please select your country!"
                );
                return;
            }
            if (mobileCerificNumber.equals("")){
                new ToastActivity().showFailed(
                        achievementView,
                        "Warnings!",
                        "Please input your contact number!"
                );
                return;
            }

            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String userData = new LocalData().getlocalData(sharedPref, "userdata");
            int userid = 0;
            try {
                JSONObject jsonObj = new JSONObject(userData);
                userid = jsonObj.getInt("user_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Call<JsonObject> userCall = service.verify(
                    new User(
                            "+"+selectedCountryCode+mobileCerificNumber.getText().toString(),
                            userid
                    ));
            userCall.enqueue(new Callback<JsonObject>() {
                 @Override
                 public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                     String status = null;
                     int verificationId = 0;

                     try {
                         status = response.body().get("status").getAsString();
                     } catch (Exception e) {
                         e.printStackTrace();
                     }

                     if (status.equals("success")){

                         verificationId = response.body().get("verification_id").getAsInt();

                         Intent mainIntent = new Intent(MobileVerificationActivity.this,EnterVerificationActivity.class);
                         mainIntent.putExtra("verification_id", verificationId);
                         mainIntent.putExtra("number", selectedCountryCode+mobileCerificNumber.getText().toString());
                         MobileVerificationActivity.this.startActivity(mainIntent);

                     }
                 }

                 @Override
                 public void onFailure(Call<JsonObject> call, Throwable t) {

                 }
            });
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("Select a item from spinner   1");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        System.out.println("Select a item from spinner bo  ");
    }
//    @Override
//    public void onBackPressed() {
//        System.out.println("You clicked back button");
//
////        if (!shouldAllowBack()) {
////            doSomething();
////        } else {
////            super.onBackPressed();
////        }
//    }
}
