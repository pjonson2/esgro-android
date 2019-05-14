package com.upventrix.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class Bank {

    @SerializedName("routing_num")
    private int routing_num;

    @SerializedName("account_num")
    private int account_num;

    @SerializedName("acc_name")
    private String acc_name;

    @SerializedName("userid")
    private int userid;

    public Bank() {
    }

    public Bank(int routing_num, int account_num, String acc_name, int userid) {
        this.routing_num = routing_num;
        this.account_num = account_num;
        this.acc_name = acc_name;
        this.userid = userid;
    }

    public int getRouting_num() {
        return routing_num;
    }

    public void setRouting_num(int routing_num) {
        this.routing_num = routing_num;
    }

    public int getAccount_num() {
        return account_num;
    }

    public void setAccount_num(int account_num) {
        this.account_num = account_num;
    }

    public String getAcc_name() {
        return acc_name;
    }

    public void setAcc_name(String acc_name) {
        this.acc_name = acc_name;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
