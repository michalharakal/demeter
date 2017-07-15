package com.fiwio.iot.demeter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.fiwio.iot.demeter.device.model.DigitalPins;
import com.fiwio.iot.demeter.discovery.NdsService;
import com.fiwio.iot.demeter.fsm.FsmBackgroundService;
import com.fiwio.iot.demeter.http.DemeterHttpServer;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private HandlerThread pioThread;
    private Handler handler;
    private DigitalPins relays;
    private DemeterHttpServer api;
    private NdsService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setup our background threading mechanism
        // this is used to send commands to the peripherals
        pioThread = new HandlerThread("pioThread");
        pioThread.start();
        handler = new Handler(pioThread.getLooper());
        // instantiate a connection to our peripheral
        relays = ((DemeterApplication) getApplication()).getDemeter();

        try {
            api = new DemeterHttpServer(relays);
            api.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        service = new NdsService(this);

        service.startServer("demeter", null);

        PeripheralManagerService service = new PeripheralManagerService();
        Log.d(TAG, "Available GPIO: " + service.getGpioList());


        final Intent intent = new Intent(Intent.ACTION_SYNC, null, this, FsmBackgroundService.class);
        startService(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pioThread.quitSafely();
        api.stop();
        service.stopServer();
    }

}
