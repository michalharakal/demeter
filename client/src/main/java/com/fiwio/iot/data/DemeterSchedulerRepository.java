package com.fiwio.iot.data;

import com.fiwo.iot.demeter.api.DefaultApi;
import com.fiwo.iot.demeter.api.model.ScheduledEvents;

import dagger.Lazy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemeterSchedulerRepository implements SchedulerRepository {

    private final Lazy<DefaultApi> networkService;

    public DemeterSchedulerRepository(Lazy<DefaultApi> defaultApi) {

        networkService = defaultApi;
    }

    @Override
    public void getSchedules(final GetSchedulerCallback callback) {
        Call<ScheduledEvents> call = networkService.get().scheduleGet();
        call.enqueue(new Callback<ScheduledEvents>() {
            @Override
            public void onResponse(Call<ScheduledEvents> call, Response<ScheduledEvents> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ScheduledEvents> call, Throwable t) {
                callback.onError(new NetworkError(t));
            }
        });

    }
}
