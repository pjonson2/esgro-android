package com.example.esgro.services;

import com.example.esgro.modals.User;
import com.example.esgro.modals.UserToken;
import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @POST("user/signup")
    Call<JsonObject> saveUser(@Body User user);

    @POST("user/login")
    Call<JsonObject> login(@Body User user);

    @POST("user/details")
    Call<JsonObject> details(@Query("uid") String uid);

    @POST("user/confirm")
    Call<JsonObject> confirm(@Body User user);

    @POST("user/verify")
    Call<JsonObject> verify(@Body User user);

    @GET("user/list")
    Call<JsonObject> usersList();

    @POST("user/token")
    Call<JsonObject> setToken(@Body UserToken userToken);

    @GET("user/token")
    Call<JsonObject> getToken(@Query("uid") String uid);

}
