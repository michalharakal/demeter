package com.fiwio.iot.demeter.di;

import com.fiwo.iot.demeter.api.DefaultApi;
import com.fiwo.iot.demeter.api.model.Demeter;
import com.fiwo.iot.demeter.api.model.DigitalOutputs;
import com.fiwo.iot.demeter.api.model.InlineResponse200;
import com.fiwo.iot.demeter.api.model.InlineResponse2001;
import com.fiwo.iot.demeter.api.model.InlineResponse2002;
import com.fiwo.iot.demeter.api.model.InlineResponse2003;
import com.fiwo.iot.demeter.api.model.ModelConfiguration;
import com.fiwo.iot.demeter.api.model.ScheduledEvents;
import com.fiwo.iot.demeter.api.model.StateMachine;
import com.fiwo.iot.demeter.api.model.Task;
import com.fiwo.iot.demeter.api.model.TriggerEvent;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;


public class InMemoryDemeterApi implements DefaultApi {
    private Demeter demeter = new Demeter();
    private final Gson gson;

    public InMemoryDemeterApi(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Call<InlineResponse2003> configGet() {
        return null;
    }

    @Override
    public Call<ModelConfiguration> configPost(@Body ModelConfiguration modelConfiguration) {
        return null;
    }

    @Override
    public Call<InlineResponse200> demeterGet() {
        final InlineResponse200 response200 = new InlineResponse200();
        response200.setData(gson.fromJson("{\"inputs\":[{\"name\":\"INP0\",\"value\":\"ON\"}]," +
                "\"relays\":[{\"name\":\"BCM23\",\"value\":\"ON\"},{\"name\":\"BCM24\",\"value\":\"OFF\"}]}", Demeter.class));
        return new Call<InlineResponse200>() {

            @Override
            public Response<InlineResponse200> execute() throws IOException {
                return null;
            }

            @Override
            public void enqueue(Callback<InlineResponse200> callback) {
                callback.onResponse(this, Response.success(response200));
            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<InlineResponse200> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
    }

    @Override
    public Call<InlineResponse2001> demeterPost(@Body DigitalOutputs digitalOutputs) {
        return null;
    }

    @Override
    public Call<InlineResponse2002> fsmGet() {
        return null;
    }

    @Override
    public Call<StateMachine> fsmPost(@Body TriggerEvent triggerEvent) {
        return null;
    }

    @Override
    public Call<ScheduledEvents> scheduleGet() {
        return null;
    }

    @Override
    public Call<ScheduledEvents> schedulePost(@Body Task task) {
        return null;
    }
}
