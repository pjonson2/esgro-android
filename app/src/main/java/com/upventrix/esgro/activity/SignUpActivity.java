package com.upventrix.esgro.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.rafaelbarbosatec.archivimentview.AchievementView;
import com.rafaelbarbosatec.archivimentview.iterface.ShowListern;
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.Notification;
import com.upventrix.esgro.modals.User;
import com.upventrix.esgro.modals.UserToken;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.LocalData;
import com.upventrix.esgro.resource.Validations;
import com.upventrix.esgro.services.NotificationService;
import com.upventrix.esgro.services.UserService;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    Button signUpBackBtn;
    Button continueBtn;

    EditText firstName;
    EditText lastName;
    EditText userName;
    EditText email;
    EditText password;
    EditText passwordReType;
    UserService service = null;
    String key = null;
    View viewById;
    CheckBox signUpCheckBox;
    ProgressBar progressBar;
    ConstraintLayout constraintLayout;
    ConstraintLayout constraintLayout2;
    Drawable background;
    private NotificationService notificationService;
    AchievementView achievementView;
    private String deviceName = "";
    private static Socket mSocket;
    String message = "";
    TextView userNameErrorLbl;
    TextView emailErrorLbl;
    TextView termsTxt;
    Dialog dialog;
    {
        try {
            mSocket = IO.socket("https://esgro-api.herokuapp.com");
        } catch (URISyntaxException e) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mSocket.on("username_unique", userName_uniqueResult);
        mSocket.on("email_unique", email_uniqueResult);

        mSocket.connect();

        idInitialization();
        setListeners();
        setValues();

        onWindowFocusChanged(true);
        viewById.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = email.getInputType(); // backup the input type
                email.setInputType(InputType.TYPE_NULL); // disable soft input
                email.onTouchEvent(event); // call native handler
                email.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });
        constraintLayout = findViewById(R.id.activity_signup);
        constraintLayout2 = findViewById(R.id.constraintLayout2);

        signUpCheckBox = findViewById(R.id.signUpCheckBox);

        constraintLayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                onWindowFocusChanged(true);
                if  (!password.getText().toString().equals("")&&passwordReType.getText().toString().equals("")){
                    passwordReType.requestFocus();
                    openKeyboard(view);
                }
//                if  (!password.getText().toString().equals(passwordReType.getText().toString())){
//                    passwordReType.requestFocus();
//                    openKeyboard(view);
//                }
                if(!passwordReType.getText().toString().equals("")){
                    hideKeyboard(view);
                    onWindowFocusChanged(true);
                }
                return false;
            }
        });
        constraintLayout2.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                onWindowFocusChanged(true);
                if  (!password.getText().toString().equals("")&&passwordReType.getText().toString().equals("")){
                    passwordReType.requestFocus();
                    openKeyboard(view);
                }
//                if  (!password.getText().toString().equals(passwordReType.getText().toString())){
//                    passwordReType.requestFocus();
//                    openKeyboard(view);
//                }
                if(!passwordReType.getText().toString().equals("")){
                    hideKeyboard(view);
                    onWindowFocusChanged(true);
                }
                return false;
            }
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    private void openKeyboard(View view) {
//        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        in.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        InputMethodManager manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    private Emitter.Listener email_uniqueResult = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            SignUpActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        boolean jsonObject =   data.getBoolean("isUnique");
                        System.out.println(jsonObject);
                        if (jsonObject){
                            System.out.println("EMAIL FINE");
                            emailErrorLbl.setVisibility(View.GONE);
                            continueBtn.setEnabled(true);
                        }else{
                            System.out.println("EMAIL isUnique");
                            emailErrorLbl.setVisibility(View.VISIBLE);
                            continueBtn.setEnabled(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };
    private Emitter.Listener userName_uniqueResult = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            SignUpActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        boolean jsonObject =   data.getBoolean("isUnique");
                        if (jsonObject){
                            System.out.println("USERNAME FINE");
                            userNameErrorLbl.setVisibility(View.GONE);
                            continueBtn.setEnabled(true);
                        }else{
                            System.out.println("USERNAME isUnique");
                            userNameErrorLbl.setVisibility(View.VISIBLE);
                            continueBtn.setEnabled(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    @SuppressLint("ResourceType")
    void idInitialization(){
        signUpBackBtn = findViewById(R.id.signUpBackBtn);
        continueBtn = findViewById(R.id.signUpContinueBtn);
        firstName = findViewById(R.id.signUpFirstNameTxt);
        lastName = findViewById(R.id.signUpLastNameTxt);
        userName = findViewById(R.id.signUpUserNameTxt);
        email = findViewById(R.id.signUpEmailTxt);
        password = findViewById(R.id.signUpPswrdTxt);
        passwordReType = findViewById(R.id.signUpPswrdReTypeTxt);
        viewById = findViewById(R.id.constraintLayout2);
        progressBar = findViewById(R.id.progressBar3);
        service = Config.getInstance().create(UserService.class);
        key = getResources().getString(R.string.userdata);
        notificationService = Config.getInstance().create(NotificationService.class);
        achievementView = findViewById(R.id.achievementView);
        userNameErrorLbl = findViewById(R.id.userNameErrorLbl);
        emailErrorLbl = findViewById(R.id.emailErrorLbl);
        termsTxt = findViewById(R.id.termsTxt);
    }

    void setListeners(){
        signUpBackBtn.setOnClickListener(signUpBack);
        continueBtn.setOnClickListener(continueBtnAction);
        progressBar.setVisibility(View.GONE);
        email.addTextChangedListener(emailChange);
        userName.addTextChangedListener(userNameChange);
        termsTxt.setOnClickListener(termsAction);
        password.addTextChangedListener(passwordChange);
        passwordReType.addTextChangedListener(reTypePasswordChange);
    }

    void setValues(){
        background = email.getBackground();
        dialog = new Dialog(this);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String temp_userdata = new LocalData().getlocalData(sharedPref, "temp_userdata")+"";

        if (temp_userdata.length() == 4){
        }else{
            System.out.println("temp_userdata   "+temp_userdata);
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(temp_userdata);
                JSONObject nameValuePairs = jsonObj.getJSONObject("nameValuePairs");

                String fName = nameValuePairs.getString("firstname");
                String lName = nameValuePairs.getString("lastname");
                String userName = nameValuePairs.getString("username");
                String email = nameValuePairs.getString("email");

                firstName.setText(fName);
                lastName.setText(lName);
                this.email.setText(email);
                this.userName.setText(userName);
                password.requestFocus();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

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
    View.OnClickListener signUpBack = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(SignUpActivity.this,LaunchedActivity.class);
            SignUpActivity.this.startActivity(mainIntent);
        }
    };

    View.OnClickListener termsAction = new View.OnClickListener() {
        public void onClick(View v) {


            dialog.setContentView(R.layout.activity_terms_and_conditions);
            dialog.show();

            Window window = dialog.getWindow();
            Button button = window.findViewById(R.id.closeBtn);
            button.setOnClickListener(closeAction);
            TextView termstxt = window.findViewById(R.id.termsAndConditionsTxt);
            termstxt.setMovementMethod(new ScrollingMovementMethod());


//            Window

        }

        View.OnClickListener closeAction = new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        };
    };

    View.OnClickListener continueBtnAction = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void onClick(View v) {

            JSONObject tempUserData = new JSONObject();
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            try {
                tempUserData.put("firstname", firstName.getText().toString());
                tempUserData.put("lastname", lastName.getText().toString());
                tempUserData.put("email", email.getText().toString());
                tempUserData.put("username", userName.getText().toString());

                new LocalData().setTempLocalData(sharedPref,tempUserData);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressBar.setVisibility(View.VISIBLE);
            continueBtn.setEnabled(false);

            boolean validations = validations();
            if (!validations){
                continueBtn.setEnabled(true);
                return;
            }

            // calling user api
            Call<JsonObject> userCall = service.saveUser(
                    new User(
                    firstName.getText().toString(),
                    lastName.getText().toString(),
                    userName.getText().toString().toLowerCase(),
                    email.getText().toString().toLowerCase(),
                    password.getText().toString()
            ));

            userCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    String status = "";
                    try {
                        status = response.body().get("status").getAsString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (status.equals("success")){

                        JsonObject userData = response.body().getAsJsonObject("userdata");

                        // set local user data

                        new LocalData().setLocalData(sharedPref,userData);

                        // re-direct next form
                        progressBar.setVisibility(View.GONE);
                        new LocalData().setTempLocalData(sharedPref,null);
                         show(
                                achievementView,
                                "Successfully!",
                                "Your details successfully saved");

                    }else{
//                        if (status.equals("duplicate")){
//                            progressBar.setVisibility(View.GONE);
//                            continueBtn.setEnabled(true);
//                            new ToastActivity().showFailed(
//                                    achievementView,
//                                    "Warnings!",
//                                    "This Email already taken !");
//
//                        }else {
                            progressBar.setVisibility(View.GONE);
                            continueBtn.setEnabled(true);
                            new ToastActivity().showFailed(
                                    achievementView,
                                    "Warnings!",
                                    "Your details saving failed");
//                        }


                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    System.out.println("Error "+t.getMessage());
                    progressBar.setVisibility(View.GONE);
                    continueBtn.setEnabled(true);
                    new ToastActivity().showFailed(
                            achievementView,
                            "Warnings!",
                            "Your details saving failed");
                }
            });

        }
    };


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean validations() {
        ShapeDrawable shape = new ShapeDrawable(new RectShape());
        boolean emailValid = new Validations().isEmailValid(email.getText().toString());

        if (firstName.getText().toString().equals("")){
            progressBar.setVisibility(View.GONE);
            shape.getPaint().setColor(Color.RED);
            shape.getPaint().setStyle(Paint.Style.STROKE);
            shape.getPaint().setStrokeWidth(1);
            firstName.setBackground(shape);
            return false;
        }else{
            firstName.setBackground(background);
        }

        if (lastName.getText().toString().equals("")){
            progressBar.setVisibility(View.GONE);
            shape.getPaint().setColor(Color.RED);
            shape.getPaint().setStyle(Paint.Style.STROKE);
            shape.getPaint().setStrokeWidth(1);
            lastName.setBackground(shape);
            return false;
        }else{
            lastName.setBackground(background);
        }

        if (!emailValid){
            progressBar.setVisibility(View.GONE);
            shape.getPaint().setColor(Color.RED);
            shape.getPaint().setStyle(Paint.Style.STROKE);
            shape.getPaint().setStrokeWidth(1);
            email.setBackground(shape);
            return false;
        }else{
            email.setBackground(background);
        }
        if(userName.getText().toString().length()<5){
            progressBar.setVisibility(View.GONE);
            message = userName.getText().toString();
            shape.getPaint().setColor(Color.RED);
            shape.getPaint().setStyle(Paint.Style.STROKE);
            shape.getPaint().setStrokeWidth(1);
            userName.setBackground(shape);
            return false;
        }else{
            userName.setBackground(background);
        }
        if (userName.getText().toString().equals("")){
            progressBar.setVisibility(View.GONE);
            shape.getPaint().setColor(Color.RED);
            shape.getPaint().setStyle(Paint.Style.STROKE);
            shape.getPaint().setStrokeWidth(1);
            userName.setBackground(shape);
            progressBar.setVisibility(View.GONE);
            return false;
        }else{
            userName.setBackground(background);
        }

        if(!password.getText().toString().equals(passwordReType.getText().toString()) || password.getText().toString().equals("") || passwordReType.getText().toString().equals("")){
            shape.getPaint().setColor(Color.RED);
            shape.getPaint().setStyle(Paint.Style.STROKE);
            shape.getPaint().setStrokeWidth(1);
            password.setBackground(shape);
            passwordReType.setBackground(shape);
            progressBar.setVisibility(View.GONE);
            return false;
        }else{
            passwordReType.setBackground(background);
            password.setBackground(background);
        }
        if(passwordReType.toString().length()>=4){
            passwordReType.setBackground(background);
        }else{
            shape.getPaint().setColor(Color.RED);
            shape.getPaint().setStyle(Paint.Style.STROKE);
            shape.getPaint().setStrokeWidth(1);
            passwordReType.setBackground(shape);
            return false;
        }

        if(password.toString().length()>=4){
            password.setBackground(background);
        }else{
            shape.getPaint().setColor(Color.RED);
            shape.getPaint().setStyle(Paint.Style.STROKE);
            shape.getPaint().setStrokeWidth(1);
            password.setBackground(shape);
            return false;
        }
        if (!signUpCheckBox.isChecked()){
            progressBar.setVisibility(View.GONE);
            new ToastActivity().showFailed(
                    achievementView,
                    "Warnings!",
                    "Accept Terms and Conditions");
             return false;
        }

        return true;
    }


    public void show(final AchievementView achievementView, String title, String body){

        achievementView
                .setTitle(title)
                .setMensage(body)
                 .setColor(R.color.colorAccent)
                .setIcon(R.drawable.checked_50px)
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
                        Log.i("LOG","dimiss");
                        setToken(SignUpActivity.this);
                    }

                    @Override
                    public void end() {
                        Log.i("LOG","end");
                    }
                })
                .show();
    }


    private void setToken( final Context context) {
        System.out.println("SetToken");
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String notification_token = new LocalData().getlocalData(sharedPref, "device_token")+"";
        System.out.println("device_token   "+notification_token);
        if(notification_token.length() == 4){
            System.out.println("notification_token  NULL ");
            return;
        }

        String userData = new LocalData().getlocalData(sharedPref, "userdata");
        int userid = 0;
        try {
            JSONObject jsonObj = new JSONObject(userData);
            userid = jsonObj.getInt("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String model = Build.MODEL;
        String brand = Build.BRAND;
        String device = Build.DEVICE;
        deviceName = brand+" "+device+" "+model;
        System.out.println("deviceName  "+deviceName);

        Call<JsonObject> jsonObjectCall = notificationService.setToken(
                new UserToken(
                        notification_token,
                        deviceName,
                        userid
                )
        );
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String status = response.body().get("status").getAsString();
                if (status.equals("success")){
                    String device_id = response.body().get("device_id").getAsString();
                    System.out.println("Status Success! "+" device_id  "+device_id+" notification_token  "+notification_token);
                     new LocalData().setDeviceId(sharedPref,device_id);

                    Intent mainIntent = new Intent(context, MobileVerificationActivity.class);
                    context.startActivity(mainIntent);
                }else{
                    new ToastActivity().showFailed(
                            achievementView,
                            "Warnings!",
                            "Failed to signup!");
                 }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("ERROR!");
            }
        });
    }

    TextWatcher emailChange = new TextWatcher() {

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
            ShapeDrawable shape = new ShapeDrawable(new RectShape());
            String searchTxt = email.getText().toString();
            boolean emailValid = new Validations().isEmailValid(email.getText().toString());
            if(emailValid){
                email.setBackground(background);
                message = email.getText().toString();
                String payload = "{ \"email\": \"" + searchTxt + "\"}";
                mSocket.emit("type_email", payload);
            }else{
                shape.getPaint().setColor(Color.RED);
                shape.getPaint().setStyle(Paint.Style.STROKE);
                shape.getPaint().setStrokeWidth(1);
                email.setBackground(shape);
            }
        }
    };
    TextWatcher userNameChange = new TextWatcher() {

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
            String searchTxt = userName.getText().toString();
            ShapeDrawable shape = new ShapeDrawable(new RectShape());
            if(userName.getText().toString().length()>=5){
                userName.setBackground(background);
                message = userName.getText().toString();
                String payload = "{ \"username\": \"" + searchTxt + "\"}";
                mSocket.emit("type_username", payload);
            }else{
                shape.getPaint().setColor(Color.RED);
                shape.getPaint().setStyle(Paint.Style.STROKE);
                shape.getPaint().setStrokeWidth(1);
                userName.setBackground(shape);
            }

        }
    };


    TextWatcher passwordChange = new TextWatcher() {

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
            String searchTxt = password.getText().toString();
            ShapeDrawable shape = new ShapeDrawable(new RectShape());
            if(searchTxt.toString().length()>=4){
                password.setBackground(background);
                continueBtn.setEnabled(true);
            }else{
                shape.getPaint().setColor(Color.RED);
                shape.getPaint().setStyle(Paint.Style.STROKE);
                shape.getPaint().setStrokeWidth(1);
                password.setBackground(shape);
                continueBtn.setEnabled(false);
            }

        }
    };


    TextWatcher reTypePasswordChange = new TextWatcher() {

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
            String searchTxt = passwordReType.getText().toString();
            ShapeDrawable shape = new ShapeDrawable(new RectShape());
            if(searchTxt.toString().length()>=4){
                passwordReType.setBackground(background);
                continueBtn.setEnabled(true);
            }else{
                shape.getPaint().setColor(Color.RED);
                shape.getPaint().setStyle(Paint.Style.STROKE);
                shape.getPaint().setStrokeWidth(1);
                passwordReType.setBackground(shape);
                continueBtn.setEnabled(false);
            }
        }
    };

}