package com.upventrix.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class DefaultCard {

    @SerializedName("card_id")
    private String card_id;

    @SerializedName("user_id")
    private int user_id;

    public DefaultCard(String card_id, int user_id) {
        this.card_id = card_id;
        this.user_id = user_id;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
