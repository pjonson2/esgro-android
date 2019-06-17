package com.upventrix.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class Bank {

    @SerializedName("routing_num")
    private int routing_num;

    @SerializedName("account_num")
    private int account_num;

    @SerializedName("acc_name")
    private String acc_name;

    @SerializedName("user_id")
    private int userid;

    @SerializedName("make_default")
    private boolean make_default;

    public Bank() {
    }

    public Bank(int routing_num, int account_num, String acc_name, int userid, boolean make_default) {
        this.routing_num = routing_num;
        this.account_num = account_num;
        this.acc_name = acc_name;
        this.userid = userid;
        this.make_default = make_default;
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

    public boolean isMake_default() {
        return make_default;
    }

    public void setMake_default(boolean make_default) {
        this.make_default = make_default;
    }
}
