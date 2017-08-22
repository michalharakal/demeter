package com.fiwio.iot.demeter.app.demeter.features.scheduler;

import com.fiwio.iot.demeter.app.app.EndpoitUrlProvider;
import com.fiwio.iot.demeter.app.data.NetworkError;
import com.fiwio.iot.demeter.app.data.SchedulerRepository;
import com.fiwo.iot.demeter.api.model.ScheduledEvents;

public class SchedulerContractPresenter implements SchedulerContract.Presenter {
    private final SchedulerContract.View view;
    private final SchedulerRepository repository;

    public SchedulerContractPresenter(SchedulerRepository repository, SchedulerContract.View view, EndpoitUrlProvider endpoitUrlProvider) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onStart() {
        getScheduler(true);
        view.showWait();

    }

    private void getScheduler(final boolean forceUpdate) {
        if (forceUpdate) {
            view.showWait();
        }

        repository.getSchedules(new SchedulerRepository.GetSchedulerCallback() {

            @Override
            public void onSuccess(ScheduledEvents events) {
                view.removeWait();
                view.setList(events);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.onFailure(networkError.toString());
            }
        });
    }

    @Override
    public void onStop() {

    }

    @Override
    public void deleteTask(String taskId) {

    }
}
