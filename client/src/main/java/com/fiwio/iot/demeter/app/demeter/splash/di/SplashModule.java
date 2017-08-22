package com.fiwio.iot.demeter.app.demeter.splash.di;

import android.content.Context;

import com.fiwio.iot.demeter.app.demeter.di.ActivityScope;
import com.fiwio.iot.demeter.app.demeter.discovery.MulticastDns;
import com.fiwio.iot.demeter.app.demeter.discovery.NdsDiscovery;

import dagger.Module;
import dagger.Provides;


@Module
public class SplashModule {


    @Provides
    @ActivityScope
    MulticastDns provideHomePresenter(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            NdsDiscovery ndsDiscovery = new NdsDiscovery(context);
            return ndsDiscovery;
        }
        return null;
    }

}
