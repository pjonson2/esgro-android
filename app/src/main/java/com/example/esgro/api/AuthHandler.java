package com.example.esgro.api;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

import com.example.esgro.modals.User;

public class AuthHandler extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public boolean isLogged(){
        return false;
    }

    public void logOut(){
        SharedPreferences.Editor editor = getSharedPreferences("userDetails", MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }

    public void signIn(User user){
//        SharedPreferences.Editor editor = getSharedPreferences("userDetails", MODE_PRIVATE).edit();
//         editor.putInt("userid", user.getUserID());
//        editor.putString("email", user.getEmail());
//        editor.putString("name",user.getUsername());
//        editor.apply();
    }
}
