package com.fiwio.iot.demeter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.fiwio.iot.demeter.api.DemeterApi;
import com.fiwio.iot.demeter.discovery.NdsService;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private HandlerThread pioThread;
    private Handler handler;
    private Relays relays;
    private DemeterApi api;
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
        relays = getLedStrip();

        try {
            api = new DemeterApi(relays);
            api.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        service = new NdsService(this);

        service.startServer("demeter", null);

        PeripheralManagerService service = new PeripheralManagerService();
        Log.d(TAG, "Available GPIO: " + service.getGpioList());
    }

    /**
     * As an example in this tutorial, you can toggle peripheral implementations with flavors,
     * could also be done at runtime with shared preferences as an example
     */
    private Relays getLedStrip() {
        if (BuildConfig.MOCK_MODE) {
            return new MockRelays();
        } else {
            return new DemeterRelays();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        pioThread.quitSafely();
        api.stop();
        service.stopServer();
    }

    private Runnable mBlinkRunnable = new Runnable() {
        public static final long INTERVAL_BETWEEN_BLINKS_MS = 1000;

        @Override
        public void run() {
            // Exit if the GPIO is already closed
            if (relays == null) {
                return;
            }

            // Step 3. Toggle the LED state
            for (int i = 0; i < 3; i++) {
                relays.inverseSwitch(i);
            }

            // Step 4. Schedule another event after delay.
            handler.postDelayed(mBlinkRunnable, INTERVAL_BETWEEN_BLINKS_MS);
        }
    };

}
