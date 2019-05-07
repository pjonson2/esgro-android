package com.example.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("userid")
    private int userID;

    @SerializedName("firstname")
    private String firstname;

    @SerializedName("lastname")
    private String lastname;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("ur_key")
    private String ur_key;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("profileImgUrl")
    private String imageUrl;

    public User() {
    }

    public User( String firstname,String lastname, String username, String email, String ur_key){
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.ur_key = ur_key;
    }

    public User(int userID, String username, String firstname, String lastname, String email, String ur_key, String mobile, String imageUrl) {
        this.userID = userID;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.ur_key = ur_key;
        this.mobile = mobile;
        this.imageUrl = imageUrl;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUr_key() {
        return ur_key;
    }

    public void setUr_key(String ur_key) {
        this.ur_key = ur_key;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
