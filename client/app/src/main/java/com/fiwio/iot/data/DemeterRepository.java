package com.fiwio.iot.data;

import com.fiwio.iot.demeter.api.DemeterApi;
import com.fiwio.iot.demeter.api.NetworkError;
import com.fiwio.iot.demeter.api.model.Demeter;
import com.fiwio.iot.demeter.api.model.Relay;

import java.util.List;

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

    @Override
    public void switchRelay(final String name, final boolean on, final GetDemeterCallback callback) {
        Call<Demeter> call = networkService.get();
        call.enqueue(new Callback<Demeter>() {
            @Override
            public void onResponse(Call<Demeter> call, Response<Demeter> response) {
                Demeter answer = response.body();

                List<Relay> relays = answer.getRelays();
                for (Relay relay : relays) {
                    if (name.equals(relay.getName())) {
                        relay.setValue(on ? "ON" : "OFF");
                        break;
                    }
                }

                Call<Demeter> post = networkService.set(answer);
                post.enqueue(new Callback<Demeter>() {
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

            @Override
            public void onFailure(Call<Demeter> call, Throwable t) {

            }
        });

    }
}
