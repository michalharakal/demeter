package com.fiwio.iot.demeter.remote;


import android.os.Handler;

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
    private final Handler handler;

    // Define the code block to be executed
    private Runnable runnableCode = new Runnable() {
        public static final long DELAY_TIME = 10000;

        @Override
        public void run() {
            getDemeter(false);
            // Repeat this the same runnable code block again another 2 seconds
            handler.postDelayed(runnableCode, DELAY_TIME);
        }
    };


    @Inject
    public RemoteControlPresenter(DemeterRepository repository, RemoteControlContract.View view) {
        this.repository = repository;
        this.view = view;

        handler = new Handler();

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
        // Start the initial runnable task by posting through the handler
        handler.post(runnableCode);
    }

    public void onStop() {
        // Removes pending code execution
        handler.removeCallbacks(runnableCode);
    }


    @Override
    public void switchRelay(String name, boolean on) {
        view.showWait();
        repository.switchRelay(name, on, new CmlRepository.GetDemeterCallback() {
            @Override
            public void onSuccess(Demeter demeter) {
                view.setList(demeter);
                view.removeWait();
            }

            @Override
            public void onError(NetworkError networkError) {
                view.onFailure(networkError.getAppErrorMessage());
                view.removeWait();
            }
        });
    }
}
