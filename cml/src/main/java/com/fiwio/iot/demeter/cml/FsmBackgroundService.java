package com.fiwio.iot.demeter.cml;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.fiwio.iot.demeter.domain.features.fsm.GardenFiniteStateMachine;
import com.fiwio.iot.demeter.fsm.FsmRunnable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FsmBackgroundService extends IntentService {
    private static final String LOG_TAG = FsmBackgroundService.class.getSimpleName();

    private Thread backgroundJob;

    private Object LOCK = new Object();

    private DigitalPins demeter;
    //private IEventBus eventBus;
    private GardenFiniteStateMachine fsm;

    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        demeter = ((DemeterApplication) getApplication()).getDemeter();

        fsm = ((DemeterApplication) getApplication()).getFsm();

        eventBus = ((DemeterApplication) getApplication()).getEventBus();

        backgroundJob = FsmRunnable.getInstance(fsm);

        backgroundJob.start();

        EventBus.getDefault().register(this);
        Log.d(LOG_TAG, "Eventbus registered");


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onFireFsmEvent(FireFsmEvent event) {
        fsm.trigger(event.command);
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
