package com.example.esgro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

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

public class SignInActivity extends AppCompatActivity {

    Button back;
    Button continueBtn;
    Button getStart;

    RequestQueue requestQueue = null;

    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin );

        idInitialization();
        setListeners();
        setValues();

        requestQueue = Volley.newRequestQueue(this);

    }

    void idInitialization(){
        back = findViewById(R.id.siginInBackBtn);
        continueBtn = findViewById(R.id.signInContinueBtn);
        getStart = findViewById(R.id.signIngetStartBtn);
        email = findViewById(R.id.signInEmailTxt);
        password= findViewById(R.id.signInPasswordTxt);
    }

    void setListeners(){
        back.setOnClickListener(backAction);
        continueBtn.setOnClickListener(continueBtnAction);
        getStart.setOnClickListener(getStartBtnAction);
    }

    void setValues(){

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
            Intent mainIntent = new Intent(SignInActivity.this,LaunchedActivity.class);
            SignInActivity.this.startActivity(mainIntent);
        }
    };

    View.OnClickListener continueBtnAction = new View.OnClickListener() {
        public void onClick(View v) {

            JSONObject signInObj = new JSONObject();
            try {
                signInObj.put("sw",email.getText());
                signInObj.put("sws",password.getText());

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,getString(R.string.url), signInObj,
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


            Intent mainIntent = new Intent(SignInActivity.this,MobileVerificationActivity.class);
            SignInActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener getStartBtnAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(SignInActivity.this,SignUpActivity.class);
            SignInActivity.this.startActivity(mainIntent);
        }
    };
}
