package com.example.esgro.resource;

import android.content.SharedPreferences;

import com.google.gson.JsonObject;

public class LocaData {

    public LocaData(SharedPreferences sharedPref, String key, JsonObject userData){
        SharedPreferences.Editor editor = sharedPref.edit();

        System.out.println("userData.keySet()  "+userData.keySet());
//        editor.putStringSet(key, userData.);
        editor.commit();
    }

}
