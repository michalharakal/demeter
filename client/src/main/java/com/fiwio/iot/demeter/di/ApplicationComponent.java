package com.fiwio.iot.demeter.di;

import com.fiwio.iot.demeter.remote.di.RemoteControlComponent;
import com.fiwio.iot.demeter.remote.di.RemoteControlModule;
import com.fiwio.iot.demeter.splash.di.SplashComponent;
import com.fiwio.iot.demeter.splash.di.SplashModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {ApplicationModule.class, NetModule.class}
)
public interface ApplicationComponent {
    RemoteControlComponent plus(RemoteControlModule remoteControlModule);

    SplashComponent plus(SplashModule remoteControlModule);
}