package com.fiwio.iot.demeter.app.demeter.features.scheduler;

import com.fiwio.iot.demeter.app.mvp.BasePresenter;
import com.fiwio.iot.demeter.app.mvp.BaseView;
import com.fiwo.iot.demeter.api.model.ScheduledEvents;

public class SchedulerContract {
    public interface View extends BaseView<SchedulerContract.Presenter> {

        void showWait();

        void removeWait();

        void onFailure(String appErrorMessage);

        void setList(ScheduledEvents events);

        void showAddTask();

    }

    public interface Presenter extends BasePresenter {

        void deleteTask(String taskId);
    }
}
