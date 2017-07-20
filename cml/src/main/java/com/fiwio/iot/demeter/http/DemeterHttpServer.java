package com.fiwio.iot.demeter.http;

import android.util.Log;

import com.fiwio.iot.demeter.api.Demeter;
import com.fiwio.iot.demeter.api.FsmCommand;
import com.fiwio.iot.demeter.api.Relay;
import com.fiwio.iot.demeter.api.Task;
import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalPins;
import com.fiwio.iot.demeter.device.model.DigitalValue;
import com.fiwio.iot.demeter.events.FireFsmEvent;
import com.fiwio.iot.demeter.events.IEventBus;
import com.fiwio.iot.demeter.fsm.FlowersFsm;
import com.fiwio.iot.demeter.scheduler.Reminder;
import com.fiwio.iot.demeter.scheduler.ReminderEngine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;
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
    private final FlowersFsm fsm;
    private final IEventBus eventBus;
    private final ReminderEngine reminderEngine;

    private final Gson gson;


    public DemeterHttpServer(DigitalPins demeterRelays, FlowersFsm fsm, IEventBus eventBus, ReminderEngine reminderEngine) throws
            IOException {
        super(8080);
        this.demeterRelays = demeterRelays;
        this.fsm = fsm;
        this.eventBus = eventBus;
        this.reminderEngine = reminderEngine;

        GsonBuilder gsonBuilder = new GsonBuilder();

        // add joda time support
        gson = gsonBuilder.create();
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
        }
        if (Method.PUT.equals(method) || Method.POST.equals(method)) {
            try {
                Map<String, String> files = new HashMap<String, String>();
                session.parseBody(files);
                // get the POST body
                String postBody = files.get("postData");
                if (path.contains("demeter")) {
                    processDemeterRequest(gson.fromJson(postBody, Demeter.class));
                }
                if (path.contains("fsm")) {
                    processFsmRequest(gson.fromJson(postBody, FsmCommand.class));
                    return newFixedLengthResponse(Response.Status.OK, "application/javascript", getFsmStatus());
                }

                if (path.contains("schedule")) {
                    processTaskRequest(gson.fromJson(postBody, Task.class));
                    return newFixedLengthResponse(Response.Status.OK, "application/javascript", getScheduleStatus());

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

    }

    private String getScheduleStatus() {
        JSONObject object = new JSONObject();
        JSONArray jobs = new JSONArray();
        try {
            object.put("jobs", jobs);

            int i = 0;
            List<Reminder> remonders = reminderEngine.getReminders();
            for (Reminder reminder : remonders) {
                final JSONObject reminderObj = new JSONObject();
                reminderObj.put("id", reminder.getId());
                reminderObj.put("timestamp", reminder.getTimestamp());
                reminderObj.put("jobid", reminder.getJobId());
                reminderObj.put("jobname", reminder.getJobName());
                jobs.put(i, reminderObj);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return object.toString();
    }

    private void processFsmRequest(FsmCommand command) {
        Log.d(TAG, "processing" + gson.toJson(command));
        reminderEngine.createNewReminder(command.getTime().getMillis(), command.getCommnad());
        eventBus.post(new FireFsmEvent(command.getCommnad(), command.getFsm()));
    }

    private String getFsmStatus() {
        JSONObject result = new JSONObject();
        JSONArray machines = new JSONArray();

        JSONObject fsmJson = new JSONObject();
        try {

            result.put("fsm", machines);

            machines.put(0, fsmJson);
            fsmJson.put("name", "garden");
            fsmJson.put("time", new DateTime());
            fsmJson.put("state", fsm.getState().getText());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    private void processDemeterRequest(Demeter demeter) {
        Log.d(TAG, "processing" + gson.toJson(demeter));
        List<Relay> relays = demeter.getRelays();
        for (Relay relay : relays) {
            demeterRelays.getOutput(relay.getName()).setValue(relay.getValue().equals("OFF") ? DigitalValue.OFF : DigitalValue.ON);
        }
    }

    private String getDemeterStatus() {
        JSONObject object = new JSONObject();
        JSONArray relays = new JSONArray();
        JSONArray inputs = new JSONArray();
        try {
            object.put("relays", relays);
            object.put("inputs", inputs);

            List<DigitalIO> inputsList = demeterRelays.getInputs();
            for (int i = 0; i < inputsList.size(); i++) {
                final JSONObject relay = new JSONObject();
                relay.put("name", inputsList.get(i).getName());
                relay.put("value", inputsList.get(i).getValue() == DigitalValue.OFF ? "OFF" : "ON");
                inputs.put(i, relay);
            }

            List<DigitalIO> outputsList = demeterRelays.getOutputs();
            for (int i = 0; i < outputsList.size(); i++) {
                final JSONObject relay = new JSONObject();
                relay.put("name", outputsList.get(i).getName());
                relay.put("value", outputsList.get(i).getValue() == DigitalValue.OFF ? "OFF" : "ON");
                relays.put(i, relay);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return object.toString();
    }
}
