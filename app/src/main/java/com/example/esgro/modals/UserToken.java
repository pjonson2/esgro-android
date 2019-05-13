package com.example.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class UserToken {

    @SerializedName("token")
    private String token;

    @SerializedName("userid")
    private int userid;

    public UserToken() {
    }

    public UserToken(String token, int userid) {
        this.token = token;
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
