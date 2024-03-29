package com.upventrix.esgro.resource;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class LocalData {


    public void setLocalData(SharedPreferences sharedPref, JsonObject userData){
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userData);
        editor.putString("userdata", json); // save json object as a string
        editor.commit();
    }

    public void setTempLocalData(SharedPreferences sharedPref, JSONObject userData){
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userData);
        editor.putString("temp_userdata", json); // save json object as a string
        editor.commit();
    }

    public String getlocalData(SharedPreferences appSharedPrefs,String key){
        String json = appSharedPrefs.getString(key, null); //  get json object as a string
        return json;
    }

    public void setNotificationToken(SharedPreferences sharedPref, String token){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("device_token", token); // save token as a string
        editor.commit();
    }
    public void setDeviceId(SharedPreferences sharedPref, String device_id){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("device_id", device_id); // save token as a string
        editor.commit();
    }


}
