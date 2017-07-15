package com.fiwio.iot.demeter.remote.di;

import com.fiwio.iot.demeter.di.ActivityScope;
import com.fiwio.iot.demeter.di.ApplicationModule;
import com.fiwio.iot.demeter.di.NetModule;
import com.fiwio.iot.demeter.remote.RemoteControlActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {RemoteControlModule.class}
)

public interface RemoteControlComponent {
    void injects(RemoteControlActivity pinAdditionFragment);
}
