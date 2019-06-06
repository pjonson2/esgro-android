package com.upventrix.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class UserToken {

    @SerializedName("device_token")
    private String token;

    @SerializedName("device_name")
    private String device_name;

    @SerializedName("user_id")
    private int userid;

    public UserToken() {
    }

    public UserToken(String token, String device_name, int userid) {
        this.token = token;
        this.device_name = device_name;
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
