package com.fiwio.iot.demeter.app.demeter.remote.di;

import com.fiwio.iot.demeter.app.data.CmlRepository;
import com.fiwio.iot.demeter.app.data.DemeterRepository;
import com.fiwio.iot.demeter.app.app.EndpoitUrlProvider;
import com.fiwio.iot.demeter.app.demeter.di.ActivityScope;
import com.fiwio.iot.demeter.app.demeter.features.remote.RemoteControlContract;
import com.fiwio.iot.demeter.app.demeter.features.remote.RemoteControlPresenter;
import com.fiwo.iot.demeter.api.DefaultApi;

import dagger.Lazy;
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
    CmlRepository provideCmlRespotiroy(Lazy<DefaultApi> defaultApi) {
        return new DemeterRepository(defaultApi);
    }

    @Provides
    @ActivityScope
    RemoteControlPresenter provideHomePresenter(CmlRepository repository, EndpoitUrlProvider endpoitUrlProvider) {
        return new RemoteControlPresenter(repository, view, endpoitUrlProvider);
    }

}
