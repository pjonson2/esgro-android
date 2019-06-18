package com.upventrix.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class UniqueID {

    @SerializedName("user_id")
    private int userId;

    public UniqueID(int userId) {
        this.userId = userId;
    }
}
