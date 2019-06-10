package com.upventrix.esgro.services;

import com.google.gson.JsonObject;
import com.upventrix.esgro.modals.Cards;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CardService {

    @POST("card/save")
    Call<JsonObject> save(@Body Cards cards);

}
