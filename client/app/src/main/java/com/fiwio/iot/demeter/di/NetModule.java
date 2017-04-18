package com.fiwio.iot.demeter.di;

import com.fiwio.iot.demeter.EndpoitUrlProvider;
import com.fiwio.iot.demeter.api.DemeterApi;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    @Singleton
    @Provides
    OkHttpClient provideNonCachedOkHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    DemeterApi provideFestivalRestService(Gson gson, OkHttpClient client, EndpoitUrlProvider endpoitUrlProvider) {

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(endpoitUrlProvider.getUrl())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return restAdapter.create(DemeterApi.class);


    }
}
