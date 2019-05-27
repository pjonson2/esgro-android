package com.upventrix.esgro.services;

import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FilesService {

    @POST("files/profile")
    Call<JsonObject> getImgUrlFromBase64(@Body com.upventrix.esgro.modals.Files files);
}
