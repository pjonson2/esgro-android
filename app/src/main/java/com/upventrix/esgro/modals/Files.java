package com.upventrix.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class Files {

    @SerializedName("base64")
    private String base64;

    public Files(String base64) {
        this.base64 = base64;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
