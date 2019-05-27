package com.upventrix.esgro.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.upventrix.esgro.R;
import com.upventrix.esgro.modals.Files;
import com.upventrix.esgro.modals.Image;
import com.upventrix.esgro.modals.User;
import com.upventrix.esgro.resource.Config;
import com.upventrix.esgro.resource.LocalData;
import com.upventrix.esgro.services.FilesService;
import com.upventrix.esgro.services.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteProfileActivity  extends AppCompatActivity{

    Button back;
    Button continueBtn;
    Button plusAddCard;
    Button plusLinkBank;
    Button skip;

    ImageView selectImg;

    TextView plusLinkbankAccount;
    CircleImageView circleImageView;
    TextView plusCrd;

    FilesService service;
    UserService userService;

    Intent intent;
    byte[] b = null;
    private final int SELECT_PHOTO = 1;
    int userid = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onWindowFocusChanged(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        idInitialization();
        setListeners();
        setValues();

    }

    void idInitialization(){
        back = findViewById(R.id.completeProfileBackBtn2);
        continueBtn = findViewById(R.id.completeProfileContinueBtn);
        plusAddCard = findViewById(R.id.plusCompleteAddBankBtn);
        plusCrd = findViewById(R.id.plusAddCrdLbl);
        plusLinkBank = findViewById(R.id.plusLinkBankBtn);
        plusLinkbankAccount = findViewById(R.id.plusLinkBankLbl);
//        selectImg = findViewById(R.id.selectImg);
        skip = findViewById(R.id.skipNowBtn);
        circleImageView = findViewById(R.id.profile_image);

        service = Config.getInstance().create(FilesService.class);
        userService = Config.getInstance().create(UserService.class);
    }

    void setListeners(){
        back.setOnClickListener(backAction);
        continueBtn.setOnClickListener(continues);
        plusAddCard.setOnClickListener(plusAddBank);
        plusCrd.setOnClickListener(plusAddBank);
        plusLinkbankAccount.setOnClickListener(plusLinkBanks);
        circleImageView.setOnClickListener(selectFile);
        skip.setOnClickListener(skipAction);
    }

    void setValues(){

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);


        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    final Uri imageUri = imageReturnedIntent.getData();
                    final InputStream imageStream;



                    try {
                        imageStream = getContentResolver().openInputStream(imageUri);

                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                        byte[] imageBytes = baos.toByteArray();
                        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                        //decode base64 string to image
                        imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                        b = new byte[0];
//                        selectedImage.setWidth(profile.getWidth());
//                        selectedImage.setHeight(profile.getHeight());
                        b =  imageBytes;

                        circleImageView.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                }
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

    View.OnClickListener backAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(CompleteProfileActivity.this,EnterVerificationActivity.class);
            CompleteProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener continues = new View.OnClickListener() {
        public void onClick(View v) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {  // start remove up
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    String userData = new LocalData().getlocalData(sharedPref, "userdata")+"";
                    try {
                        JSONObject jsonObj = new JSONObject(userData);
                        userid = jsonObj.getInt("userid");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String encodedImage = "";

                    try{
                        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                        if (encodedImage==null){
                            System.out.println("Error");
                            return;
                        }
                    }catch(Exception e){
                        System.out.println("Error");
                        return;
                    }

                    Call<JsonObject> imgUrlFromBase64 = service.getImgUrlFromBase64(new Files(encodedImage));
                     imgUrlFromBase64.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            String status = "";
                             try {
                                status = response.body().get("filename").getAsString();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (!status.equals("")) {
                                System.out.println("AWS URL   .....  " + status);

                                Image user = new Image(status,userid);

                                Call<JsonObject> profile = userService.profile(user);
                                profile.enqueue(new Callback<JsonObject>() {
                                    @Override
                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                        String status = "";
                                        status = response.body().get("status").getAsString();

                                        if (status.equals("success")) {
                                            System.out.println("success   ..... "+status);

                                            // code to re-direct page
                                            Intent mainIntent = new Intent(CompleteProfileActivity.this, DisputeNoHistoryActivity.class);
                                            CompleteProfileActivity.this.startActivity(mainIntent);
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<JsonObject> call, Throwable t) {

                                    }
                                });

                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });
                }
            }, 400);   // start remove to down
        }
    };

    View.OnClickListener plusAddBank = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(CompleteProfileActivity.this, AddCardActivity.class);
            mainIntent.putExtra("identifier","CompleteProfileActivity");
            CompleteProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener plusLinkBanks = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(CompleteProfileActivity.this,BankListActivity.class);
            mainIntent.putExtra("identifier","CompleteProfileActivity");
            CompleteProfileActivity.this.startActivity(mainIntent);
        }
    };
    View.OnClickListener selectFile = new View.OnClickListener() {
        public void onClick(View v) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
        }
    };
    View.OnClickListener skipAction = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(CompleteProfileActivity.this,DisputeNoHistoryActivity.class);
            CompleteProfileActivity.this.startActivity(mainIntent);
        }
    };

    @Override
    public void onBackPressed() {
        System.out.println("You clicked back button");
//        if (!shouldAllowBack()) {
//            doSomething();
//        } else {
//            super.onBackPressed();
//        }
    }


}
