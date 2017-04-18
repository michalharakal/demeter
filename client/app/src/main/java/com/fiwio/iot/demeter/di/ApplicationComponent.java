package com.fiwio.iot.demeter.di;

import com.fiwio.iot.demeter.remote.di.RemoteControlComponent;
import com.fiwio.iot.demeter.remote.di.RemoteControlModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {ApplicationModule.class, NetModule.class}
)
public interface ApplicationComponent {
    RemoteControlComponent plus(RemoteControlModule remoteControlModule);
}