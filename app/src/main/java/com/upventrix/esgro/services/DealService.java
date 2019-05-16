package com.upventrix.esgro.services;

import com.google.gson.JsonObject;
import com.upventrix.esgro.modals.Deal;
import com.upventrix.esgro.modals.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DealService {

    @GET("deal/all")
    Call<JsonObject> dealAll(@Query("uid") String uid);

    @POST("deal/save")
    Call<JsonObject> saveDEal(@Body Deal deal);

}
