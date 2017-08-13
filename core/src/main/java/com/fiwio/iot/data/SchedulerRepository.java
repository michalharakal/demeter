package com.fiwio.iot.data;


import com.fiwo.iot.demeter.api.model.ScheduledEvents;
import com.fiwo.iot.demeter.api.model.Task;

public interface SchedulerRepository {
    void getSchedules(final GetSchedulerCallback callback);

    void addTask(Task task, final GetSchedulerCallback callback);

    void removeTask(final GetSchedulerCallback callback);


    interface GetSchedulerCallback {
        void onSuccess(ScheduledEvents demeter);

        void onError(NetworkError networkError);
    }

}
