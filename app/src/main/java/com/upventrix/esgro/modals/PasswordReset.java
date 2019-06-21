package com.upventrix.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class PasswordReset {

    @SerializedName("veri_id")
    private int veri_id;

    @SerializedName("new_pwd")
    private String new_pw;

    public PasswordReset(int veri_id, String new_pw) {
        this.veri_id = veri_id;
        this.new_pw = new_pw;
    }
}
