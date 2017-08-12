package com.fiwio.iot.data;


import com.fiwo.iot.demeter.api.DefaultApi;
import com.fiwo.iot.demeter.api.model.Demeter;
import com.fiwo.iot.demeter.api.model.DigitalOutputs;
import com.fiwo.iot.demeter.api.model.Relay;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemeterRepository implements CmlRepository {
    private final Lazy<DefaultApi> networkService;

    @Inject
    public DemeterRepository(Lazy<DefaultApi> networkService) {
        this.networkService = networkService;
    }

    @Override
    public void getDemeter(final GetDemeterCallback callback) {

        Call<Demeter> call = networkService.get().demeterGet();
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
    public void switchRelay(final String name, final boolean on, final PostDemeterCallback callback) {
        Call<Demeter> call = networkService.get().demeterGet();
        call.enqueue(new Callback<Demeter>() {
            @Override
            public void onResponse(Call<Demeter> call, Response<Demeter> response) {
                Demeter answer = response.body();

                List<Relay> relays = answer.getRelays();

                DigitalOutputs postOutputs = new DigitalOutputs();
                postOutputs.setRelays(relays);

                for (Relay relay : relays) {
                    if (name.equals(relay.getName())) {
                        relay.setValue(on ? "ON" : "OFF");
                        break;
                    }
                }


                Call<DigitalOutputs> post = networkService.get().demeterPost(postOutputs);
                post.enqueue(new Callback<DigitalOutputs>() {
                    @Override
                    public void onResponse(Call<DigitalOutputs> call, Response<DigitalOutputs> response) {
                        callback.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<DigitalOutputs> call, Throwable t) {
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
