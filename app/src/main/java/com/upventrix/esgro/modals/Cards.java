package com.upventrix.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class Cards {

    @SerializedName("user_id")
    private int user_id;

    @SerializedName("source")
    private String source;

    @SerializedName("email")
    private String email;

    @SerializedName("make_default")
    private Boolean make_default;

    public Cards(int user_id, String source, String email, Boolean make_default) {
        this.user_id = user_id;
        this.source = source;
        this.email = email;
        this.make_default = make_default;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getMake_default() {
        return make_default;
    }

    public void setMake_default(Boolean make_default) {
        this.make_default = make_default;
    }
}
