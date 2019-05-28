package com.upventrix.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class Request {

    private int userid;

    private String name;

    private String days;

    private String image;

    public Request(int userid, String name, String days, String image) {
        this.userid = userid;
        this.name = name;
        this.days = days;
        this.image = image;
    }

    public Request() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
