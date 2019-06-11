package com.upventrix.esgro.services;

import com.google.gson.JsonObject;
import com.upventrix.esgro.modals.Cards;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CardService {

    @POST("card/save")
    Call<JsonObject> save(@Body Cards cards);

    @POST("card/list")
    Call<JsonObject> getAll(@Query("uid") String id);
}
