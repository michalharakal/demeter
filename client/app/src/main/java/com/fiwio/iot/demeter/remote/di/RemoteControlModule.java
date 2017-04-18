package com.fiwio.iot.demeter.remote.di;

import com.fiwio.iot.demeter.api.DemeterService;
import com.fiwio.iot.demeter.di.ActivityScope;
import com.fiwio.iot.demeter.remote.HomePresenter;
import com.fiwio.iot.demeter.remote.RemoteControlContract;

import dagger.Module;
import dagger.Provides;

@Module
public class RemoteControlModule {

    private final RemoteControlContract.View view;

    public RemoteControlModule(RemoteControlContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    HomePresenter provideHomePresenter(DemeterService demeterService) {
        return new HomePresenter(demeterService, view);
    }

}
