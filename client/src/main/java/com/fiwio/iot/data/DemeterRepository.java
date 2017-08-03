package com.fiwio.iot.data;


import com.fiwio.iot.demeter.remote.NetworkError;
import com.fiwo.iot.demeter.api.DefaultApi;
import com.fiwo.iot.demeter.api.model.Demeter;
import com.fiwo.iot.demeter.api.model.DigitalOutputs;
import com.fiwo.iot.demeter.api.model.InlineResponse200;
import com.fiwo.iot.demeter.api.model.InlineResponse2001;
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

        Call<InlineResponse200> call = networkService.get().demeterGet();
        call.enqueue(new Callback<InlineResponse200>() {
            @Override
            public void onResponse(Call<InlineResponse200> call, Response<InlineResponse200> response) {
                callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<InlineResponse200> call, Throwable t) {
                callback.onError(new NetworkError(t));
            }
        });
    }

    @Override
    public void switchRelay(final String name, final boolean on, final PostDemeterCallback callback) {
        Call<InlineResponse200> call = networkService.get().demeterGet();
        call.enqueue(new Callback<InlineResponse200>() {
            @Override
            public void onResponse(Call<InlineResponse200> call, Response<InlineResponse200> response) {
                Demeter answer = response.body().getData();

                List<Relay> relays = answer.getRelays();

                DigitalOutputs postOutputs = new DigitalOutputs();
                postOutputs.setRelays(relays);

                for (Relay relay : relays) {
                    if (name.equals(relay.getName())) {
                        relay.setValue(on ? "ON" : "OFF");
                        break;
                    }
                }


                Call<InlineResponse2001> post = networkService.get().demeterPost(postOutputs);
                post.enqueue(new Callback<InlineResponse2001>() {
                    @Override
                    public void onResponse(Call<InlineResponse2001> call, Response<InlineResponse2001> response) {
                        callback.onSuccess(response.body().getData());

                    }

                    @Override
                    public void onFailure(Call<InlineResponse2001> call, Throwable t) {
                        callback.onError(new NetworkError(t));
                    }
                });
            }

            @Override
            public void onFailure(Call<InlineResponse200> call, Throwable t) {

            }
        });
    }
}
