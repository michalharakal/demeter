package com.fiwio.iot.demeter;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import android.content.RestrictionsManager;

import com.evernote.android.job.JobManager;
import com.fiwio.iot.demeter.configuration.Configuration;
import com.fiwio.iot.demeter.configuration.ConfigurationProvider;
import com.fiwio.iot.demeter.configuration.DemeterConfigurationProvider;
import com.fiwio.iot.demeter.device.mock.MockDigitalPins;
import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalPins;
import com.fiwio.iot.demeter.device.pifacedigital2.DemeterDigitalPins;
import com.fiwio.iot.demeter.events.DemeterEventBus;
import com.fiwio.iot.demeter.events.IEventBus;
import com.fiwio.iot.demeter.fsm.GardenFiniteStateMachine;
import com.fiwio.iot.demeter.scheduler.ReminderEngine;
import com.fiwio.iot.demeter.scheduler.ReminderJobCreator;
import com.google.android.things.device.TimeManager;

import net.danlew.android.joda.JodaTimeAndroid;

import java.io.IOException;

public class DemeterApplication extends Application {

    private DigitalPins demeter;
    private GardenFiniteStateMachine fsm;
    private IEventBus eventBus;
    private Configuration configuration;
    private ConfigurationProvider configurationProvider;
    private ReminderEngine reminderEngine;

    @Override
    public void onCreate() {
        super.onCreate();

        TimeManager tm =  TimeManager.getInstance();
        tm.setTimeZone("Europe/Berlin");

        JodaTimeAndroid.init(this);

        configuration = new Configuration(this);
        configurationProvider = new DemeterConfigurationProvider(configuration);

        demeter = createDeviceImageInstance();
        fsm = createFlowersFsm();
        eventBus = new DemeterEventBus();

        reminderEngine = new ReminderEngine(this, eventBus);

        JobManager.create(this).addJobCreator(new ReminderJobCreator(reminderEngine));
    }

    private DigitalPins createDeviceImageInstance() {
        if (BuildConfig.MOCK_MODE) {
            return new MockDigitalPins();
        } else {
            try {
                return new DemeterDigitalPins(fsm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private GardenFiniteStateMachine createFlowersFsm() {
        DigitalIO barrel_pump = demeter.getOutput("BCM23");
        DigitalIO barrel_valve = demeter.getOutput("BCM24");
        DigitalIO float_input = demeter.getInput("INP0");

        return new GardenFiniteStateMachine(barrel_pump, barrel_valve, float_input, configurationProvider);
    }

    public DigitalPins getDemeter() {
        return demeter;
    }

    public Configuration getConfiguration() {
        return configuration;
    }


    public GardenFiniteStateMachine getFsm() {
        return fsm;
    }

    public IEventBus getEventBus() {
        return eventBus;
    }

    public ReminderEngine getRemainderEngine() {
        return reminderEngine;
    }
}
