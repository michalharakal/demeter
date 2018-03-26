package com.fiwio.iot.demeter.app.demeter.splash.di;

import com.fiwio.iot.demeter.app.demeter.splash.SplashActivity;

import dagger.Subcomponent;

@Subcomponent(
        modules = {SplashModule.class}
)

public interface SplashComponent {
    void injects(SplashActivity splashActivity);
}


