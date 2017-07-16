package com.fiwio.iot.demeter.http;

import android.util.Log;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.fiwio.iot.demeter.api.Demeter;
import com.fiwio.iot.demeter.api.FsmCommand;
import com.fiwio.iot.demeter.api.Relay;
import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalPins;
import com.fiwio.iot.demeter.device.model.DigitalValue;
import com.fiwio.iot.demeter.fsm.FlowersFsm;
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

    private final Gson gson;


    /**
     * Constructs an HTTP server on given port.
     *
     * @param demeterRelays
     * @param fsm
     */
    public DemeterHttpServer(DigitalPins demeterRelays, FlowersFsm fsm) throws IOException {
        super(8080);
        this.demeterRelays = demeterRelays;
        this.fsm = fsm;

        GsonBuilder gsonBuilder = new GsonBuilder();

        // add joda time support
        gson = Converters.registerDateTime(gsonBuilder).create();
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
            } catch (IOException ioe) {
                return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
            } catch (ResponseException re) {
                return newFixedLengthResponse(re.getStatus(), MIME_PLAINTEXT, re.getMessage());
            }
        }

        return newFixedLengthResponse(Response.Status.OK, "application/javascript", getDemeterStatus());
    }

    private void processFsmRequest(FsmCommand command) {
        Log.d(TAG, "processing" + gson.toJson(command));
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
