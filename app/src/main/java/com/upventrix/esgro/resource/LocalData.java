package com.upventrix.esgro.resource;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class LocalData {


    public void setLocalData(SharedPreferences sharedPref, JsonObject userData){
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userData);
        editor.putString("userdata", json); // save json object as a string
        editor.commit();
    }

    public String getlocalData(SharedPreferences appSharedPrefs,String key){
        String json = appSharedPrefs.getString(key, null); //  get json object as a string
        return json;
    }

}
