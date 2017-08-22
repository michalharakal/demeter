package com.fiwio.iot.demeter.app.demeter.addtask.di;

import com.fiwio.iot.demeter.app.app.EndpoitUrlProvider;
import com.fiwio.iot.demeter.app.data.DemeterSchedulerRepository;
import com.fiwio.iot.demeter.app.data.SchedulerRepository;
import com.fiwio.iot.demeter.app.demeter.addtask.AddSchedulerTaskPresenter;
import com.fiwio.iot.demeter.app.demeter.di.ActivityScope;
import com.fiwio.iot.demeter.app.demeter.features.addtask.AddSchedulerTaskContract;
import com.fiwo.iot.demeter.api.DefaultApi;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;

@Module
public class AddSchedulerTaskModule {

    private final AddSchedulerTaskContract.View view;

    public AddSchedulerTaskModule(AddSchedulerTaskContract.View view) {

        this.view = view;
    }

    @Provides
    @ActivityScope
    SchedulerRepository provideCmlRespotiroy(Lazy<DefaultApi> defaultApi) {
        return new DemeterSchedulerRepository(defaultApi);
    }


    @Provides
    @ActivityScope
    AddSchedulerTaskContract.Presenter provideHomePresenter(SchedulerRepository repository,
                                                            EndpoitUrlProvider
                                                                    endpoitUrlProvider) {
        return new AddSchedulerTaskPresenter(repository, view, endpoitUrlProvider);
    }

}
