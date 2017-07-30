package com.fiwio.iot.demeter.device.mock;

import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalIoCallback;
import com.fiwio.iot.demeter.device.model.DigitalPins;

import java.util.ArrayList;
import java.util.List;

public class MockDigitalPins implements DigitalPins {

    private static final String TAG = MockDigitalPins.class.getSimpleName();

    private List<DigitalIO> inputs = new ArrayList<>();
    private List<DigitalIO> relays = new ArrayList<>();

    public MockDigitalPins() {
        relays.add(new MockDigitalIO("BCM23"));
        relays.add(new MockDigitalIO("BCM24"));
        relays.add(new MockDigitalIO("BCM22"));

        inputs.add(new MockDigitalIO("BCM26"));
        inputs.add(new MockDigitalIO("BCM16"));
    }

    @Override
    public List<DigitalIO> getInputs() {
        return inputs;
    }

    @Override
    public List<DigitalIO> getOutputs() {
        return relays;
    }

    @Override
    public DigitalIO getInput(String name) {
        for (DigitalIO input : inputs) {
            if (input.getName().equals(name)) {
                return input;
            }
        }
        return null;
    }

    @Override
    public DigitalIO getOutput(String name) {
        for (DigitalIO output : relays) {
            if (output.getName().equals(name)) {
                return output;
            }
        }
        return null;
    }

    @Override
    public void registerInputCallback(DigitalIoCallback callback) {

    }
}
