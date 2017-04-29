package com.fiwio.iot.demeter.splash.di;

import com.fiwio.iot.demeter.di.ActivityScope;
import com.fiwio.iot.demeter.splash.SplashActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {SplashModule.class}
)

public interface SplashComponent {
    void injects(SplashActivity splashActivity);
}


