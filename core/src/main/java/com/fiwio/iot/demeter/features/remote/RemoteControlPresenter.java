package com.fiwio.iot.demeter.features.remote;

import com.fiwio.iot.app.EndpoitUrlProvider;
import com.fiwio.iot.data.CmlRepository;
import com.fiwio.iot.data.NetworkError;
import com.fiwo.iot.demeter.api.model.Demeter;
import com.fiwo.iot.demeter.api.model.DigitalOutputs;


public class RemoteControlPresenter implements RemoteControlContract.Presenter {
    private final RemoteControlContract.View view;
    private final CmlRepository repository;
    private final EndpoitUrlProvider endpoitUrlProvider;

    // Define the code block to be executed
    private Runnable runnableCode = new Runnable() {
        public static final long DELAY_TIME = 300;

        @Override
        public void run() {
            getDemeter(false);
            // Repeat this the same runnable code block again another 2 seconds
            // handler.postDelayed(runnableCode, DELAY_TIME);
        }
    };

    public RemoteControlPresenter(CmlRepository repository, RemoteControlContract.View view,
                                  EndpoitUrlProvider endpoitUrlProvider) {
        this.repository = repository;
        this.view = view;
        this.endpoitUrlProvider = endpoitUrlProvider;

//        handler = new Handler();

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
}
