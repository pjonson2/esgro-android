package com.upventrix.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("url")
    private String url;

    @SerializedName("userid")
    private int userid;

    public Image(String url, int userid) {
        this.url = url;
        this.userid = userid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Image() {
    }
}
