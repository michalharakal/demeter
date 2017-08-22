package com.fiwio.iot.demeter.app.demeter.splash.di;

import com.fiwio.iot.demeter.app.demeter.di.ActivityScope;
import com.fiwio.iot.demeter.app.demeter.splash.SplashActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {SplashModule.class}
)

public interface SplashComponent {
    void injects(SplashActivity splashActivity);
}


