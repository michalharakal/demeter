package com.fiwio.iot.demeter.app.di;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.fiwio.iot.demeter.app.InMemoryDemeterApi;
import com.fiwo.iot.demeter.api.DefaultApi;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

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
        return Converters.registerDateTime(new GsonBuilder()).create();
    }

    @Provides
    @Singleton
    DefaultApi provideFestivalRestService(Gson gson) {
        return new InMemoryDemeterApi(gson);
    }
}
