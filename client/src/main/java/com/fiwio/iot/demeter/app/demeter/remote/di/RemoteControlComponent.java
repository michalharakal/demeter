package com.fiwio.iot.demeter.app.demeter.remote.di;

import com.fiwio.iot.demeter.app.demeter.remote.RemoteControlFragment;

import dagger.Subcomponent;

@Subcomponent(
        modules = {RemoteControlModule.class}
)

public interface RemoteControlComponent {
    void injects(RemoteControlFragment remoteControlFragment);
}
