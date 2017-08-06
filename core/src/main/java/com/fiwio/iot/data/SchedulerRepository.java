package com.fiwio.iot.data;


import com.fiwo.iot.demeter.api.model.ScheduledEvents;

public interface SchedulerRepository {
    void getSchedules(final GetSchedulerCallback callback);

    interface GetSchedulerCallback {
        void onSuccess(ScheduledEvents demeter);

        void onError(NetworkError networkError);
    }
}
