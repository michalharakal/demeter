package com.fiwio.iot.demeter;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;

import com.evernote.android.job.JobManager;
import com.fiwio.iot.demeter.device.mock.MockDigitalPins;
import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalPins;
import com.fiwio.iot.demeter.device.pifacedigital2.DemeterDigitalPins;
import com.fiwio.iot.demeter.events.DemeterEventBus;
import com.fiwio.iot.demeter.events.IEventBus;
import com.fiwio.iot.demeter.fsm.GardenFiniteStateMachine;
import com.fiwio.iot.demeter.scheduler.ReminderEngine;
import com.fiwio.iot.demeter.scheduler.ReminderJobCreator;

import net.danlew.android.joda.JodaTimeAndroid;

import java.io.IOException;

public class DemeterApplication extends Application {

    private DigitalPins demeter;
    private GardenFiniteStateMachine fsm;
    private IEventBus eventBus;

    @Override
    public void onCreate() {
        super.onCreate();

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.setTimeZone("Europe/Berlin");

        JodaTimeAndroid.init(this);

        demeter = createDeviceImageInstance();
        fsm = createFlowersFsm();
        eventBus = new DemeterEventBus();

        ReminderEngine reminderEngine = new ReminderEngine(this, eventBus);

        JobManager.create(this).addJobCreator(new ReminderJobCreator(reminderEngine));
    }

    private DigitalPins createDeviceImageInstance() {
        if (BuildConfig.MOCK_MODE) {
            return new MockDigitalPins();
        } else {
            try {
                return new DemeterDigitalPins();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private GardenFiniteStateMachine createFlowersFsm() {
        DigitalIO barrel_pump = demeter.getOutput("BCM23");
        DigitalIO barrel_valve = demeter.getOutput("BCM24");

        return new GardenFiniteStateMachine(barrel_pump, barrel_valve, 1000 * 60);
    }

    public DigitalPins getDemeter() {
        return demeter;
    }

    public GardenFiniteStateMachine getFsm() {
        return fsm;
    }

    public IEventBus getEventBus() {
        return eventBus;
    }
}
