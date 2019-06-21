package com.upventrix.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class UniqueEmail {

    @SerializedName("email")
    private String email;

    public UniqueEmail(String email) {
        this.email = email;
    }
}
