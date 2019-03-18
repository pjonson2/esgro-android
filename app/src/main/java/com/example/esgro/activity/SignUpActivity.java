package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.esgro.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    Button signUpBackBtn;
    Button continueBtn;

    RequestQueue requestQueue = null;

    EditText firstName;
    EditText lastName;
    EditText userName;
    EditText email;
    EditText password;
    EditText passwordReType;

    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        idInitialization();
        setListeners();
        setValues();

        requestQueue = Volley.newRequestQueue(this);
    }

    void idInitialization(){
        signUpBackBtn = findViewById(R.id.signUpBackBtn);
        continueBtn = findViewById(R.id.signUpContinueBtn);

        firstName = findViewById(R.id.signUpFirstNameTxt);
        lastName = findViewById(R.id.signUpLastNameTxt);
        userName = findViewById(R.id.signUpUserNameTxt);
        email = findViewById(R.id.signUpEmailTxt);
        password = findViewById(R.id.signUpPswrdTxt);
        passwordReType = findViewById(R.id.signUpPswrdReTypeTxt);
    }

    void setListeners(){
        signUpBackBtn.setOnClickListener(signUpBack);
        continueBtn.setOnClickListener(continueBtnAction);
    }

    void setValues(){

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
    View.OnClickListener continueBtnAction = new View.OnClickListener() {
        public void onClick(View v) {

            JSONObject signUpObj = new JSONObject();
            try {

                signUpObj.put("firstName",firstName.getText());
                signUpObj.put("lastName",lastName.getText());
                signUpObj.put("userName",userName.getText());
                signUpObj.put("email",email.getText());
                signUpObj.put("password",password.getText());


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,getString(R.string.url), signUpObj,
                        new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("VOLLEY", response.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof AuthFailureError) {
                                    Log.e("AuthFailureError", error.getMessage(), error);
                                }
                                else {
                                    Log.e("Auth Not Failure", error.getMessage(), error);
                                }
                            }
                        });

                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 2, 1));
                requestQueue.add(jsonObjectRequest);

            } catch (JSONException e) {
                e.printStackTrace();
            }

//
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                    Request.Method.POST,
//                    url,
//                    null,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            System.out.println("response  is "+response.toString());
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            System.out.println("error  is "+error.toString());
//                        }
//                    }
//            );
//            requestQueue.add(jsonObjectRequest);

            Intent mainIntent = new Intent(SignUpActivity.this,MobileVerificationActivity.class);
            SignUpActivity.this.startActivity(mainIntent);
        }
    };
}