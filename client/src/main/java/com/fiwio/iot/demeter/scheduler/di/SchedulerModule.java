package com.fiwio.iot.demeter.scheduler.di;

import com.fiwio.iot.app.EndpoitUrlProvider;
import com.fiwio.iot.data.DemeterSchedulerRepository;
import com.fiwio.iot.data.SchedulerRepository;
import com.fiwio.iot.demeter.di.ActivityScope;
import com.fiwio.iot.demeter.features.scheduler.SchedulerContract;
import com.fiwio.iot.demeter.features.scheduler.SchedulerContractPresenter;
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
    @ActivityScope
    SchedulerRepository provideCmlRespotiroy(Lazy<DefaultApi> defaultApi) {
        return new DemeterSchedulerRepository(defaultApi);
    }


    @Provides
    @ActivityScope
    SchedulerContract.Presenter provideHomePresenter(SchedulerRepository repository,
                                                    EndpoitUrlProvider
    endpoitUrlProvider) {
        return new SchedulerContractPresenter(repository, view, endpoitUrlProvider);
    }

}
