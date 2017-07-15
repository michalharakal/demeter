package com.fiwio.iot.demeter.device.mock;

import android.util.Log;

import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalIOType;
import com.fiwio.iot.demeter.device.model.DigitalValue;
import com.fiwio.iot.demeter.device.rpi3.DemeterInput;

public class MockDigitalIO implements DigitalIO {

    private static final String TAG = DemeterInput.class.getSimpleName();

    private DigitalValue value;
    private final String name;

    public MockDigitalIO(String name) {
        this.name = name;
    }

    @Override
    public void setValue(DigitalValue value) {
        this.value = value;
        Log.d(TAG, getName() + ((value == DigitalValue.ON) ? "ON" : "OFF"));

    }

    @Override
    public DigitalValue getValue() {
        return value;
    }

    @Override
    public DigitalIOType getType() {
        return DigitalIOType.INPUT;
    }

    @Override
    public String getName() {
        return name;
    }
}
