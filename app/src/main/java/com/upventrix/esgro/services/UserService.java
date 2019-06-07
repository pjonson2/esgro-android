package com.upventrix.esgro.services;

import com.upventrix.esgro.modals.Image;
import com.upventrix.esgro.modals.Password;
import com.upventrix.esgro.modals.User;
import com.upventrix.esgro.modals.UserToken;
import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @POST("user/signup")
    Call<JsonObject> saveUser(@Body User user);

    @POST("user/update")
    Call<JsonObject> updateUSer(@Body User user);

    @POST("user/login")
    Call<JsonObject> login(@Body User user);

    @POST("user/details")
    Call<JsonObject> details(@Query("uid") String uid);

    @POST("user/confirm")
    Call<JsonObject> confirm(@Body User user);

    @POST("user/verify")
    Call<JsonObject> verify(@Body User user);

    @POST("user/profile")
    Call<JsonObject> profile(@Body Image image);

    @GET("user/list")
    Call<JsonObject> usersList(@Query("user_id") String user_id);

//    @POST("user/token")
//    Call<JsonObject> setToken(@Body UserToken userToken);
//
//    @GET("user/token")
//    Call<JsonObject> getToken(@Query("uid") String uid);

    @POST("user/changekey")
    Call<JsonObject> changeKey(@Body Password password);
}
