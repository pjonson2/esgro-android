package com.upventrix.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class DefaultBank {

    @SerializedName("bank_id")
    private int bank_id;

    @SerializedName("user_id")
    private int user_id;

    public DefaultBank(int bank_id, int user_id) {
        this.bank_id = bank_id;
        this.user_id = user_id;
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
