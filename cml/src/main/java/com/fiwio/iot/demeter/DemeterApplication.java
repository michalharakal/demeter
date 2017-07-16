package com.fiwio.iot.demeter;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;

import com.fiwio.iot.demeter.device.mock.MockDigitalPins;
import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalPins;
import com.fiwio.iot.demeter.device.rpi3.DemeterDigitalPins;
import com.fiwio.iot.demeter.fsm.FlowersFsm;

import net.danlew.android.joda.JodaTimeAndroid;

public class DemeterApplication extends Application {

    private DigitalPins demeter;
    private FlowersFsm fsm;

    @Override
    public void onCreate() {
        super.onCreate();

        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        am.setTimeZone("Europe/Berlin");

        JodaTimeAndroid.init(this);

        demeter = createDeviceImageInstance();
        fsm = createFlowersFsm();
    }

    private DigitalPins createDeviceImageInstance() {
        if (BuildConfig.MOCK_MODE) {
            return new MockDigitalPins();
        } else {
            return new DemeterDigitalPins();
        }
    }

    private FlowersFsm createFlowersFsm() {
        DigitalIO barrel_pump = demeter.getOutput("BCM23");
        DigitalIO barrel_valve = demeter.getOutput("BCM24");

        return new FlowersFsm(barrel_pump, barrel_valve, 1000 * 60);
    }

    public DigitalPins getDemeter() {
        return demeter;
    }

    public FlowersFsm getFsm() {
        return fsm;
    }

}
