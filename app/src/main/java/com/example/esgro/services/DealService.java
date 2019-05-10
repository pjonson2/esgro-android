package com.example.esgro.services;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DealService {

    @GET("deal/all")
    Call<JsonObject> dealAll(@Query("uid") String uid);

}
