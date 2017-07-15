package com.fiwio.iot.demeter.fsm;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

import com.fiwio.iot.demeter.DemeterApplication;
import com.fiwio.iot.demeter.device.model.DigitalPins;

public class FsmBackgroundService extends IntentService {
    private static final String LOG_TAG = FsmBackgroundService.class.getSimpleName();

    private Thread backgroundJob;

    private Object LOCK = new Object();
    private DigitalPins demeter;

    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        demeter = ((DemeterApplication) getApplication()).getDemeter();

        FlowersFsm fsm = ((DemeterApplication) getApplication()).getFsm();

        backgroundJob = FsmRunnable.getInstance(fsm);

        backgroundJob.start();
    }


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FsmBackgroundService(String name) {
        super(name);
    }

    /**
     * Creates an IntentService.  Required by AndroidManifest.xml
     */
    public FsmBackgroundService() {
        super(LOG_TAG);
    }


    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
