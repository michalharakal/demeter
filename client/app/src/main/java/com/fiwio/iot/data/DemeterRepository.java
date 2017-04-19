package com.fiwio.iot.data;

import com.fiwio.iot.demeter.api.DemeterApi;
import com.fiwio.iot.demeter.api.NetworkError;
import com.fiwio.iot.demeter.api.model.Demeter;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemeterRepository implements CmlRepository {
    private final DemeterApi networkService;

    @Inject
    public DemeterRepository(DemeterApi networkService) {
        this.networkService = networkService;
    }

    @Override
    public void getDemeter(final GetDemeterCallback callback) {

        Call<Demeter> call = networkService.get();
        call.enqueue(new Callback<Demeter>() {
            @Override
            public void onResponse(Call<Demeter> call, Response<Demeter> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Demeter> call, Throwable t) {
                callback.onError(new NetworkError(t));
            }
        });
    }
}
