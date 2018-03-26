package com.fiwio.iot.demeter.app;

import com.fiwo.iot.demeter.api.DefaultApi;
import com.fiwo.iot.demeter.api.model.Demeter;
import com.fiwo.iot.demeter.api.model.DigitalOutputs;
import com.fiwo.iot.demeter.api.model.InlineResponse200;
import com.fiwo.iot.demeter.api.model.InlineResponse2001;
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
    public Call<InlineResponse2001> configGet() {
        return null;
    }

    @Override
    public Call<ModelConfiguration> configPost(@Body ModelConfiguration modelConfiguration) {
        return null;
    }

    @Override
    public Call<Demeter> demeterGet() {
        final Demeter response200 = gson.fromJson("{\"inputs\":[{\"name\":\"INP0\",\"value\":\"ON\"}]," +
                "\"relays\":[{\"name\":\"BCM23\",\"value\":\"ON\"},{\"name\":\"BCM24\",\"value\":\"OFF\"}]}", Demeter.class);
        return new Call<Demeter>() {

            @Override
            public Response<Demeter> execute() throws IOException {
                return null;
            }

            @Override
            public void enqueue(Callback<Demeter> callback) {
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
            public Call<Demeter> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
    }

    @Override
    public Call<DigitalOutputs> demeterPost(@Body DigitalOutputs digitalOutputs) {
        return null;
    }

    @Override
    public Call<InlineResponse200> fsmGet() {
        return null;
    }

    @Override
    public Call<StateMachine> fsmPost(@Body TriggerEvent triggerEvent) {
        return null;
    }

    @Override
    public Call<ScheduledEvents> scheduleGet() {
        String cmd = "[{\"command\":\"irrigate\",\"fsm\":\"garden\",\"id\":14,\"time\":\"2017-08-03T04:15:00.758Z\"},{\"command\":\"irrigate\",\"fsm\":\"garden\",\"id\":22,\"time\":\"2017-08-03T17:15:00.758Z\"},{\"command\":\"fill\",\"fsm\":\"garden\",\"id\":27,\"time\":\"2017-08-03T19:00:00.758Z\"},{\"command\":\"irrigate\",\"fsm\":\"garden\",\"id\":15,\"time\":\"2017-08-04T04:15:00.758Z\"},{\"command\":\"irrigate\",\"fsm\":\"garden\",\"id\":23,\"time\":\"2017-08-04T17:15:00.758Z\"},{\"command\":\"fill\",\"fsm\":\"garden\",\"id\":26,\"time\":\"2017-08-04T19:00:00.758Z\"},{\"command\":\"irrigate\",\"fsm\":\"garden\",\"id\":16,\"time\":\"2017-08-05T04:15:00.758Z\"},{\"command\":\"irrigate\",\"fsm\":\"garden\",\"id\":24,\"time\":\"2017-08-05T17:15:00.758Z\"},{\"command\":\"fill\",\"fsm\":\"garden\",\"id\":25,\"time\":\"2017-08-05T19:00:00.758Z\"}]";

        final ScheduledEvents scheduledEvents = gson.fromJson(cmd, ScheduledEvents.class);

        return new Call<ScheduledEvents>() {

            @Override
            public Response<ScheduledEvents> execute() throws IOException {
                return null;
            }

            @Override
            public void enqueue(Callback<ScheduledEvents> callback) {
                callback.onResponse(this, Response.success(scheduledEvents));
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
            public Call<ScheduledEvents> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
    }

    @Override
    public Call<ScheduledEvents> schedulePost(@Body Task task) {
        return null;
    }
}
