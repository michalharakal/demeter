package com.fiwio.iot.demeter.android.ui.feature.splash.di;

import com.fiwio.iot.demeter.android.ui.feature.splash.SplashActivity;

import org.jetbrains.annotations.NotNull;

import dagger.Subcomponent;

@Subcomponent(
        modules = {SplashModule.class}
)

public interface SplashComponent {
    void injects(@NotNull SplashActivity splashActivity);
}


