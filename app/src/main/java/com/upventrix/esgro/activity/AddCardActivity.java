package com.upventrix.esgro.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.upventrix.esgro.R;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddCardActivity extends AppCompatActivity {

    Button back;
    Button addcrd;

    EditText cardNumber;
    EditText expDate;
    EditText cvv;

    String identifier = "";


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
        this.showDatePickerDialog();


        expDate.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = expDate.getInputType(); // backup the input type
                expDate.setInputType(InputType.TYPE_NULL); // disable soft input
                expDate.onTouchEvent(event); // call native handler
                expDate.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });
    }

    // Create and show a DatePickerDialog when click button.
    private void showDatePickerDialog()
    {
        // Get open DatePickerDialog button.
         expDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new OnDateSetListener instance. This listener will be invoked when user click ok button in DatePickerDialog.
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        StringBuffer strBuf = new StringBuffer();
                        strBuf.append(month+1);
                        strBuf.append("/");
                        strBuf.append((year+"").substring(2));

                        expDate.setText(strBuf.toString());
                    }

                };

                // Get current year, month and day.
                Calendar now = Calendar.getInstance();
                int year = now.get(java.util.Calendar.YEAR);
                int month = now.get(java.util.Calendar.MONTH);

                // Create the new DatePickerDialog instance.
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddCardActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, 0);
                datePickerDialog.updateDate( year, month,0);
               // Set dialog icon and title.
                 datePickerDialog.setTitle("Please select year and month.");

                // Popup the dialog.
                datePickerDialog.show();
            }
        });
    }


    void idInitialization(){
        back = findViewById(R.id.addCardBackBtn);
        addcrd = findViewById(R.id.addCrdBtn);
        cardNumber = findViewById(R.id.CardCardNumberTxt);
        expDate = findViewById(R.id.CardExpDateTxt);
        cvv = findViewById(R.id.cardCvvTxt);
        cardNumber.addTextChangedListener(checkNumbers);
    }


    void setListeners(){
        back.setOnClickListener(addCardBAck);
        addcrd.setOnClickListener(addCardAction);
        expDate.setOnClickListener(cvvAction);
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
            Intent mainIntent = new Intent(AddCardActivity.this, HomePageActivity.class);
            AddCardActivity.this.startActivity(mainIntent);
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
            if (cardNumber.getText().toString().length() == 4){

            }
            if (cardNumber.getText().toString().length() == 9){

            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            System.out.println("afterTextChanged");
        }
    };

}

