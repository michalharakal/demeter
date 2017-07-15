package com.fiwio.iot.demeter.device.mock;

import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalPins;

import java.util.List;

public class MockRelays implements DigitalPins {

    private static final String TAG = MockRelays.class.getSimpleName();


    @Override
    public List<DigitalIO> getInputs() {
        return null;
    }

    @Override
    public List<DigitalIO> getOutputs() {
        return null;
    }

    @Override
    public DigitalIO getInput(String name) {
        return null;
    }

    @Override
    public DigitalIO getOutput(String name) {
        return null;
    }
}
