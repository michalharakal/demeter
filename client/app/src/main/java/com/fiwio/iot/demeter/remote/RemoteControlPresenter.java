package com.fiwio.iot.demeter.remote;


import com.fiwio.iot.demeter.api.DemeterService;
import com.fiwio.iot.demeter.api.NetworkError;
import com.fiwio.iot.demeter.api.model.Demeter;
import com.fiwio.iot.demeter.di.ActivityScope;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

@ActivityScope
public class RemoteControlPresenter implements RemoteControlContract.Presenter {
    private final RemoteControlContract.View view;
    private final DemeterService service;
    private CompositeSubscription subscriptions;

    public RemoteControlPresenter(DemeterService service, RemoteControlContract.View view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getDemeter() {
        view.showWait();

        Subscription subscription = service.get(new DemeterService.GetCityListCallback() {
            @Override
            public void onSuccess(Demeter demeter) {
                view.removeWait();
                view.setList(demeter);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        });

        subscriptions.add(subscription);
    }

    @Override
    public void onStart() {
        getDemeter();
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }

    @Override
    public void setRelay(String name, boolean on) {
    }
}
