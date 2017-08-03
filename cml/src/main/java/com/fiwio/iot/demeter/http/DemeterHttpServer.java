package com.fiwio.iot.demeter.http;

import android.util.Log;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.fiwio.iot.demeter.configuration.Configuration;
import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalPins;
import com.fiwio.iot.demeter.device.model.DigitalValue;
import com.fiwio.iot.demeter.events.FireFsmEvent;
import com.fiwio.iot.demeter.events.IEventBus;
import com.fiwio.iot.demeter.fsm.GardenFiniteStateMachine;
import com.fiwio.iot.demeter.scheduler.Reminder;
import com.fiwio.iot.demeter.scheduler.ReminderEngine;
import com.fiwo.iot.demeter.api.model.Demeter;
import com.fiwo.iot.demeter.api.model.Input;
import com.fiwo.iot.demeter.api.model.ModelConfiguration;
import com.fiwo.iot.demeter.api.model.Relay;
import com.fiwo.iot.demeter.api.model.ScheduledEvent;
import com.fiwo.iot.demeter.api.model.ScheduledEvents;
import com.fiwo.iot.demeter.api.model.StateMachine;
import com.fiwo.iot.demeter.api.model.Task;
import com.fiwo.iot.demeter.api.model.TriggerEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class DemeterHttpServer extends NanoHTTPD {

    private static final String TAG = DemeterHttpServer.class.getSimpleName();

    private final DigitalPins demeterRelays;
    private final GardenFiniteStateMachine fsm;
    private final IEventBus eventBus;
    private final ReminderEngine reminderEngine;
    private final Configuration confgurationEngine;

    private final Gson gson;


    public DemeterHttpServer(DigitalPins demeterRelays, GardenFiniteStateMachine fsm, IEventBus eventBus,
                             ReminderEngine reminderEngine, Configuration configuration) throws
            IOException {
        super(8080);
        this.demeterRelays = demeterRelays;
        this.fsm = fsm;
        this.eventBus = eventBus;
        this.reminderEngine = reminderEngine;
        this.confgurationEngine = configuration;

        // gson with JodaTime support
        gson = Converters.registerDateTime(new GsonBuilder()).create();
    }


    @Override
    public Response serve(IHTTPSession session) {
        String path = session.getUri();

        Method method = session.getMethod();

        if (method == Method.GET) {
            if (path.contains("demeter")) {
                return newFixedLengthResponse(Response.Status.OK, "application/javascript", getDemeterStatus());
            }
            if (path.contains("fsm")) {
                return newFixedLengthResponse(Response.Status.OK, "application/javascript", getFsmStatus());
            }
            if (path.contains("schedule")) {
                return newFixedLengthResponse(Response.Status.OK, "application/javascript", getScheduleStatus());
            }
            if (path.contains("knx")) {
                return newFixedLengthResponse(Response.Status.OK, "application/javascript", getKnxStatus());
            }

            if (path.contains("config")) {
                return newFixedLengthResponse(Response.Status.OK, "application/javascript", getConfigStatus());
            }


        }
        if (Method.DELETE.equals(method)) {
            if (path.contains("schedule")) {
                String[] segments = session.getUri().split("/");

                reminderEngine.removeReminderById(Integer.valueOf(segments[2]));
                return newFixedLengthResponse(Response.Status.OK, "application/javascript", getScheduleStatus());
            }
        }

        if (Method.PUT.equals(method) || Method.POST.equals(method)) {
            try {
                Map<String, String> files = new HashMap<>();
                session.parseBody(files);
                // get the POST body
                String postBody = files.get("postData");
                if (path.contains("demeter")) {
                    processDemeterRequest(gson.fromJson(postBody, Demeter.class));
                }
                if (path.contains("fsm")) {
                    processFsmRequest(gson.fromJson(postBody, TriggerEvent.class));
                    return newFixedLengthResponse(Response.Status.OK, "application/javascript", getFsmStatus());
                }

                if (path.contains("schedule")) {

                    try {
                        processTaskRequest(gson.fromJson(postBody, Task.class));
                    } catch (JsonSyntaxException e) {
                        Task task = new Task();
                        DateTime dateTime = new DateTime();
                        dateTime.plusMinutes(1);
                        task.setTime(dateTime);
                        task.setCommand("close");
                        task.setFsm("garden");
                        processTaskRequest(task);

                    }
                    return newFixedLengthResponse(Response.Status.OK, "application/javascript", getScheduleStatus());
                }

                if (path.contains("config")) {
                    processConfigurationRequest(gson.fromJson(postBody, ModelConfiguration.class));
                    return newFixedLengthResponse(Response.Status.OK, "application/javascript", getConfigStatus());
                }


            } catch (IOException ioe) {
                return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
            } catch (ResponseException re) {
                return newFixedLengthResponse(re.getStatus(), MIME_PLAINTEXT, re.getMessage());
            }
        }

        return newFixedLengthResponse(Response.Status.OK, "application/javascript", getDemeterStatus());
    }


    private String getKnxStatus() {
        JSONObject object = new JSONObject();
        JSONArray plugs = new JSONArray();
        try {
            object.put("plugs", plugs);

            JSONObject plug1 = new JSONObject();
            plug1.put("id", 1);
            plug1.put("name", "Licht Küche");
            plug1.put("value", true);
            plugs.put(0, plug1);

            JSONObject plug2 = new JSONObject();
            plug2.put("id", 2);
            plug2.put("name", "Licht Bad");
            plug2.put("value", true);
            plugs.put(1, plug2);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return object.toString();
    }

    private void processTaskRequest(Task task) {
        Log.d(TAG, "processing" + gson.toJson(task));
        Log.d(TAG, "current date time=" + new DateTime().toString());
        reminderEngine.createNewReminder(task.getTime().getMillis(), task.getCommand());
    }

    private String getScheduleStatus() {
        ScheduledEvents scheduledEvents = new ScheduledEvents();

        for (Reminder reminder : reminderEngine.getReminders()) {
            ScheduledEvent scheduledEvent = new ScheduledEvent();

            DateTime dt = new DateTime(reminder.getTimestamp(), DateTimeZone.UTC);
            scheduledEvent.setTime(dt.toDateTime());
            scheduledEvent.setCommand(reminder.getJobName());
            scheduledEvent.setId(reminder.getId());
            // for multiple FSMs you have to map names to jobIds
            scheduledEvent.setFsm(fsm.getName());

            scheduledEvents.add(scheduledEvent);
        }
        return gson.toJson(scheduledEvents);
    }

    private String getConfigStatus() {
        return gson.toJson(confgurationEngine.getConfiguration());
    }


    private void processConfigurationRequest(ModelConfiguration newConfiguration) {
        Log.d(TAG, "processing" + gson.toJson(newConfiguration));
        confgurationEngine.setConfiguration(newConfiguration);
    }


    private void processFsmRequest(TriggerEvent command) {
        Log.d(TAG, "processing" + gson.toJson(command));
        eventBus.post(new FireFsmEvent(command.getFsm(), command.getCommand()));
    }

    private String getFsmStatus() {
        StateMachine stateMachine = new StateMachine();
        stateMachine.setName(fsm.getName());
        stateMachine.setState(fsm.getState().getText());

        // iterate over Enum to get list of commands as text
        for (GardenFiniteStateMachine.Events event : GardenFiniteStateMachine.Events.values()) {
            stateMachine.addCommnadsItem(event.getText());
        }

        return gson.toJson(stateMachine);
    }

    private void processDemeterRequest(Demeter demeter) {
        Log.d(TAG, "processing" + gson.toJson(demeter));
        List<Relay> relays = demeter.getRelays();
        for (Relay relay : relays) {
            demeterRelays.getOutput(relay.getName()).setValue(relay.getValue().equals("OFF") ? DigitalValue.OFF : DigitalValue.ON);
        }
    }

    private String getDemeterStatus() {
        Demeter demeter = new Demeter();

        for (DigitalIO digitalInput : demeterRelays.getInputs()) {
            Input inputObject = new Input();
            inputObject.setName(digitalInput.getName());
            inputObject.setValue(digitalInput.getValue() == DigitalValue.OFF ? "OFF" : "ON");
            demeter.addInputsItem(inputObject);
        }

        for (DigitalIO digitalOutput : demeterRelays.getOutputs()) {
            Relay outputObject = new Relay();
            outputObject.setName(digitalOutput.getName());
            outputObject.setValue(digitalOutput.getValue() == DigitalValue.OFF ? "OFF" : "ON");
            demeter.addRelaysItem(outputObject);
        }

        return gson.toJson(demeter);
    }
}
