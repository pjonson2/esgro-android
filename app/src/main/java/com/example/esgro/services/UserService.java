package com.example.esgro.services;

import com.example.esgro.modals.User;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("user/signup")
    Call<JsonObject> saveUser(@Body User user);

    @POST("user/login")
    Call<JsonObject> login(@Body User user);

    @POST("user/details")
    Call<JsonObject> details(@Body User user);

    @POST("user/confirm")
    Call<JsonObject> confirm(@Body User user);

    @POST("user/verify")
    Call<JsonObject> verify(@Body User user);


}
