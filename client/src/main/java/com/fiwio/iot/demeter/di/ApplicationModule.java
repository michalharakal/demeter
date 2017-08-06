package com.fiwio.iot.demeter.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.fiwio.iot.app.EndpoitUrlProvider;
import com.fiwio.iot.demeter.app.StringEndpoitUrlProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class ApplicationModule {

    private final Application app;

    private final SharedPreferences mSharedPrefernces;

    public ApplicationModule(Application app) {
        this.app = app;
        mSharedPrefernces = app.getSharedPreferences("app", 0);
    }

    @Provides
    @Singleton
    Context provideContext() {
        return app;
    }


    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return mSharedPrefernces;
    }

    @Provides
    @Singleton
    EndpoitUrlProvider provideEndpoitUrlProvider() {
        return new StringEndpoitUrlProvider();
    }

}
