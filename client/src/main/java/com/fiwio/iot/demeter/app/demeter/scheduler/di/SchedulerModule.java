package com.fiwio.iot.demeter.app.demeter.scheduler.di;

import com.fiwio.iot.demeter.app.app.EndpoitUrlProvider;
import com.fiwio.iot.demeter.app.data.DemeterSchedulerRepository;
import com.fiwio.iot.demeter.app.data.SchedulerRepository;
import com.fiwio.iot.demeter.app.demeter.features.scheduler.SchedulerContract;
import com.fiwio.iot.demeter.app.demeter.features.scheduler.SchedulerContractPresenter;
import com.fiwo.iot.demeter.api.DefaultApi;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;

@Module
public class SchedulerModule {

    private final SchedulerContract.View view;

    public SchedulerModule(SchedulerContract.View view) {
        this.view = view;
    }

    @Provides
    SchedulerRepository provideCmlRespotiroy(Lazy<DefaultApi> defaultApi) {
        return new DemeterSchedulerRepository(defaultApi);
    }


    @Provides
    SchedulerContract.Presenter provideHomePresenter(SchedulerRepository repository,
                                                    EndpoitUrlProvider
    endpoitUrlProvider) {
        return new SchedulerContractPresenter(repository, view, endpoitUrlProvider);
    }

}
