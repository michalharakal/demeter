package com.fiwio.iot.demeter.remote;


import com.fiwio.iot.data.CmlRepository;
import com.fiwio.iot.data.DemeterRepository;
import com.fiwio.iot.demeter.api.NetworkError;
import com.fiwio.iot.demeter.api.model.Demeter;
import com.fiwio.iot.demeter.di.ActivityScope;

import javax.inject.Inject;


@ActivityScope
public class RemoteControlPresenter implements RemoteControlContract.Presenter {
    private final RemoteControlContract.View view;
    private final DemeterRepository repository;

    @Inject
    public RemoteControlPresenter(DemeterRepository repository, RemoteControlContract.View view) {
        this.repository = repository;
        this.view = view;
    }

    public void getDemeter() {
        view.showWait();

        repository.getDemeter(new CmlRepository.GetDemeterCallback() {
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
    }

    @Override
    public void onStart() {
        getDemeter();
    }

    public void onStop() {

    }

    @Override
    public void setRelay(String name, boolean on) {
    }
}
