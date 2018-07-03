package com.fiwio.iot.demeter.app.demeter.features.remote;

import com.fiwio.iot.demeter.app.app.EndpoitUrlProvider;
import com.fiwio.iot.demeter.app.data.CmlRepository;
import com.fiwio.iot.demeter.app.data.NetworkError;
import com.fiwo.iot.demeter.api.model.Demeter;
import com.fiwo.iot.demeter.api.model.DigitalOutputs;


public class RemoteControlPresenter implements RemoteControlContract.Presenter {
    private final RemoteControlContract.View view;
    private final CmlRepository repository;
    private final EndpoitUrlProvider endpoitUrlProvider;

    public RemoteControlPresenter(CmlRepository repository, RemoteControlContract.View view,
                                  EndpoitUrlProvider endpoitUrlProvider) {
        this.repository = repository;
        this.view = view;
        this.endpoitUrlProvider = endpoitUrlProvider;


    }

    public void getDemeter(final boolean forceUpdate) {
        if (forceUpdate) {
            view.showWait();
        }

        repository.getDemeter(new CmlRepository.GetDemeterCallback() {
            @Override
            public void onSuccess(Demeter demeter) {
                if (forceUpdate) {
                    view.removeWait();
                }
                view.setList(demeter);
            }

            @Override
            public void onError(NetworkError networkError) {
                if (forceUpdate) {
                    view.removeWait();
                }
                view.onFailure(networkError.getAppErrorMessage());

            }
        });
    }

    @Override
    public void onStart() {
        getDemeter(true);
        view.showEndpoint(endpoitUrlProvider.getUrl());
    }

    public void onStop() {
    }


    @Override
    public void switchRelay(String name, boolean on) {
        view.showWait();
        repository.switchRelay(name, on, new CmlRepository.PostDemeterCallback() {
            @Override
            public void onSuccess(final DigitalOutputs outputs) {
                repository.getDemeter(new CmlRepository.GetDemeterCallback() {
                    @Override
                    public void onSuccess(Demeter demeter) {
                        demeter.setRelays(outputs.getRelays());
                        view.setList(demeter);
                        view.removeWait();
                    }

                    @Override
                    public void onError(NetworkError networkError) {

                    }
                });

            }

            @Override
            public void onError(NetworkError networkError) {
                view.onFailure(networkError.getAppErrorMessage());
                view.removeWait();
            }
        });
    }

    @Override
    public void switchAllOff() {
        view.showWait();
        repository.switchAllOff(new CmlRepository.PostDemeterCallback() {
            @Override
            public void onSuccess(final DigitalOutputs outputs) {
                repository.getDemeter(new CmlRepository.GetDemeterCallback() {
                    @Override
                    public void onSuccess(Demeter demeter) {
                        demeter.setRelays(outputs.getRelays());
                        view.setList(demeter);
                        view.removeWait();
                    }

                    @Override
                    public void onError(NetworkError networkError) {

                    }
                });

            }

            @Override
            public void onError(NetworkError networkError) {
                view.onFailure(networkError.getAppErrorMessage());
                view.removeWait();
            }
        });
    }
}
