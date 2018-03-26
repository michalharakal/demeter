package com.fiwio.iot.demeter.app.di;


import com.fatboyindustrial.gsonjodatime.Converters;
import com.fiwio.iot.demeter.app.StringEndpoitUrlProvider;
import com.fiwio.iot.demeter.app.app.EndpoitUrlProvider;
import com.fiwo.iot.demeter.api.DefaultApi;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {


    @Singleton
    @Provides
    OkHttpClient provideNonCachedOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return Converters.registerDateTime(gsonBuilder).create();
    }


    @Provides
    @Singleton
    DefaultApi provideRestApi(Gson gson, OkHttpClient client, EndpoitUrlProvider provider) {

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(provider.getUrl())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return restAdapter.create(DefaultApi.class);
    }
}
