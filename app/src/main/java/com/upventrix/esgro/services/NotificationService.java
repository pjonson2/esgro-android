package com.upventrix.esgro.services;

import com.google.gson.JsonObject;
import com.upventrix.esgro.modals.UserToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NotificationService {
    @POST("notifications/android")
    Call<JsonObject> setToken(@Body UserToken userToken);

    @GET("notifications/token")
    Call<JsonObject> getToken(@Query("dev_id") String dev_id);

}
