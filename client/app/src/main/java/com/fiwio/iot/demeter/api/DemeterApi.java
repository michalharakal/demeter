package com.fiwio.iot.demeter.api;


import com.fiwio.iot.demeter.api.model.Demeter;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface DemeterApi {
    @GET("demeter")
    Call<Demeter> get();

    @POST("demeter")
    Call<Demeter> set(@Body Demeter demeter);
}
