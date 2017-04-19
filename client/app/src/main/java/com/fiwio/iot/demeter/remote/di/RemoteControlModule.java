package com.fiwio.iot.demeter.remote.di;

import com.fiwio.iot.data.DemeterRepository;
import com.fiwio.iot.demeter.di.ActivityScope;
import com.fiwio.iot.demeter.remote.RemoteControlContract;
import com.fiwio.iot.demeter.remote.RemoteControlPresenter;

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
    RemoteControlPresenter provideHomePresenter(DemeterRepository repository) {
        return new RemoteControlPresenter(repository, view);
    }

}
