package com.example.esgro.services;

import com.example.esgro.modals.Bank;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BankService {

    @POST("bank/account")
    Call<JsonObject> save(@Body Bank bank);
}
