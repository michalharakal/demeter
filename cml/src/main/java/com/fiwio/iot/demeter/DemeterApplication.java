package com.fiwio.iot.demeter;

import android.app.Application;

import com.fiwio.iot.demeter.domain.features.fsm.GardenFiniteStateMachine;
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
