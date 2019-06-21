package com.upventrix.esgro.services;

import com.upventrix.esgro.modals.Image;
import com.upventrix.esgro.modals.Password;
import com.upventrix.esgro.modals.PasswordReset;
import com.upventrix.esgro.modals.UniqueEmail;
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

    @POST("user/changekey")
    Call<JsonObject> changeKey(@Body Password password);

    @POST("user/forgot")
    Call<JsonObject> forgot(@Body UniqueEmail user);

    @POST("user/validate-pin")
    Call<JsonObject> verifyPin(@Body User user);

    @POST("user/reset-forgotten")
    Call<JsonObject> resetPw(@Body PasswordReset passwordReset);
}
