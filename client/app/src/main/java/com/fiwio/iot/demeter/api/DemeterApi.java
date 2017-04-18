package com.fiwio.iot.demeter.api;


import com.fiwio.iot.demeter.api.model.Demeter;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;


public interface DemeterApi {
    @GET("demeter")
    Observable<Demeter> get();

    @POST("demeter")
    Observable<Demeter> set(@Body Demeter demeter);
}
