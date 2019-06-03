package com.upventrix.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class Password {

    @SerializedName("user_id")
    private int userID;

    @SerializedName("old_pwd")
    private String old_pwd;

    @SerializedName("new_pwd")
    private String new_pwd;

    public Password(int userID, String old_pwd, String new_pwd) {
        this.userID = userID;
        this.old_pwd = old_pwd;
        this.new_pwd = new_pwd;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getOld_pwd() {
        return old_pwd;
    }

    public void setOld_pwd(String old_pwd) {
        this.old_pwd = old_pwd;
    }

    public String getNew_pwd() {
        return new_pwd;
    }

    public void setNew_pwd(String new_pwd) {
        this.new_pwd = new_pwd;
    }
}
