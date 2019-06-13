package com.upventrix.esgro.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rafaelbarbosatec.archivimentview.AchievementView;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.Cards;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.LocalData;
import com.upventrix.esgro.services.CardService;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCardActivity extends AppCompatActivity {

    Button back;
    Button addcrd;

    EditText cardNumber;
    EditText zip;

    String identifier = "";
    CardInputWidget mCardInputWidget = null;
    private ConstraintLayout constraintLayout;
    private CardService cardService;
    int userid = 0;
    String email = "";
    AchievementView achievementView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_credit_debit);

        identifier = getIntent().getStringExtra("identifier");

        idInitialization();
        setListeners();
        setValues();

    }


    void idInitialization(){
        back = findViewById(R.id.addCardBackBtn);
        addcrd = findViewById(R.id.addCrdBtn);
        mCardInputWidget = new CardInputWidget(this);
        mCardInputWidget =  findViewById(R.id.card_input_widget);
        constraintLayout = findViewById(R.id.add_credit_debit);
        zip = findViewById(R.id.zipCOdeTXt);
        cardService = Config.getInstance().create(CardService.class);
        achievementView = findViewById(R.id.achievementView);
    }

    public boolean onClickSomething(String cardNumber, Integer cardExpMonth, Integer cardExpYear, String cardCVC,String zip) {
        Card card = new Card(
                cardNumber,
                cardExpMonth,
                cardExpYear,
                cardCVC
        );
        card.setAddressZip(zip);
        if (!card.validateCard()) {
            // Show errors
            return false;
        }
        return true;
    }
    void setListeners(){
        back.setOnClickListener(addCardBAck);
        addcrd.setOnClickListener(addCardAction);
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

    void setValues(){

    }
    private void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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

    View.OnClickListener addCardBAck = new View.OnClickListener() {
        public void onClick(View v) {

            if (identifier.equals("TransferToCardActivity")){
                Intent mainIntent = new Intent(AddCardActivity.this, TransferToCardActivity.class);
                AddCardActivity.this.startActivity(mainIntent);
            }

            if (identifier.equals("BankAndCards2Activity")){
                Intent mainIntent = new Intent(AddCardActivity.this, BankAndCards2Activity.class);
                AddCardActivity.this.startActivity(mainIntent);
            }

            if (identifier.equals("CompleteProfileActivity")){
                Intent mainIntent = new Intent(AddCardActivity.this, CompleteProfileActivity.class);
                AddCardActivity.this.startActivity(mainIntent);
            }
        }
    };
    View.OnClickListener addCardAction = new View.OnClickListener() {
        public void onClick(View v) {
            Context context = getApplicationContext();
            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String userData = new LocalData().getlocalData(sharedPref, "userdata");

            try {
                JSONObject jsonObj = new JSONObject(userData);
                userid = jsonObj.getInt("user_id");
                email = jsonObj.getString("email");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String zipCOde = zip.getText().toString();

            Card cardToSave = mCardInputWidget.getCard();

            if (cardToSave == null) {

                new  ToastActivity().showFailed(
                        achievementView,
                        "Invalid!",
                        "Card Details Invalid. try again");
                return;
            }
            boolean b = onClickSomething(cardToSave.getNumber(), cardToSave.getExpMonth(), cardToSave.getExpYear(), cardToSave.getCVC(),zipCOde);

            if (!b) {
                new  ToastActivity().showFailed(
                        achievementView,
                        "Invalid!",
                        "Card Details Invalid. try again");
                return;
            }
            final boolean b1 = cardToSave.validateNumber();
            final boolean b2 = cardToSave.validateCVC();

            if(zipCOde.equals("")){
                new  ToastActivity().showFailed(
                        achievementView,
                        "Invalid!",
                        "Invalid zip code. try again");
                return;
            }
            if (b1 && b2) {
                addcrd.setEnabled(false);
                System.out.println("Card Valid ");
                cardToSave.setAddressZip(zipCOde);
                Stripe stripe = new Stripe(AddCardActivity.this, "pk_test_5njifX9nB71rW0gxMU8WEP0c");
                stripe.createToken(
                        cardToSave,
                        new TokenCallback() {
                            public void onSuccess(Token token) {
                                // Send token to your server
                                System.out.println("TOKEN "+token.getCard().toJson());
                                Call<JsonObject> save = cardService.save(new Cards(
                                        userid,
                                        token.getId(),
                                        email
                                ));
                                save.enqueue(new Callback<JsonObject>() {
                                    @Override
                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                        System.out.println("Response   "+ response.body());

                                        boolean status = response.body().get("status").getAsBoolean();
                                        if (status){
                                            new  ToastActivity().showOK(
                                                achievementView,
                                                "Successfully!",
                                                "Card details successfully saved",
                                                AddCardActivity.this,
                                                BankAndCards2Activity.class
                                            );
                                         }else{
                                            addcrd.setEnabled(true);
                                            new  ToastActivity().showFailed(
                                                achievementView,
                                                "Warnings!",
                                                "Card details saving failed!"
                                            );
                                         }
                                    }

                                    @Override
                                    public void onFailure(Call<JsonObject> call, Throwable t) {
                                        System.out.println("ERROR ....");
                                        addcrd.setEnabled(true);
                                    }
                                });
                            }
                            public void onError(Exception error) {
                                System.out.println("ERROR   "+error);
                                addcrd.setEnabled(true);
                                // Show localized error message
                            }
                        }
                );
            } else {
                CharSequence text = "Invalid Card";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return;
            }

        }
    };



        View.OnClickListener cvvAction = new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(999);
            }
        };

        TextWatcher checkNumbers = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cardNumber.getText().toString().length() == 4) {

                }
                if (cardNumber.getText().toString().length() == 9) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("afterTextChanged");
            }
        };

}

