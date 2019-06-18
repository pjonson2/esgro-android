package com.upventrix.esgro.services;

import com.google.gson.annotations.SerializedName;
import com.upventrix.esgro.modals.Bank;
import com.google.gson.JsonObject;
import com.upventrix.esgro.modals.UniqueID;
import com.upventrix.esgro.modals.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BankService {

    @POST("bank/account")
    Call<JsonObject> save(@Body Bank bank);

    @POST("bank/list")
    Call<JsonObject> getAll(@Body UniqueID id);
}
