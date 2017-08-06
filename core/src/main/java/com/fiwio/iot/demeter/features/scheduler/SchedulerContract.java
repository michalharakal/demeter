package com.fiwio.iot.demeter.features.scheduler;

import com.fiwio.iot.mvp.BasePresenter;
import com.fiwio.iot.mvp.BaseView;
import com.fiwo.iot.demeter.api.model.ScheduledEvents;

public class SchedulerContract {
    public interface View extends BaseView<SchedulerContract.Presenter> {

        void showWait();

        void removeWait();

        void onFailure(String appErrorMessage);

        void setList(ScheduledEvents events);
    }

    public interface Presenter extends BasePresenter {

        void switchRelay(String name, boolean on);
    }
}
