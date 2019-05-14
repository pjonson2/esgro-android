package com.upventrix.esgro.services;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DealService {

    @GET("deal/all")
    Call<JsonObject> dealAll(@Query("uid") String uid);

}
