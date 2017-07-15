package com.fiwio.iot.demeter.device.rpi3;

import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalPins;
import com.google.android.things.pio.PeripheralManagerService;

import java.util.ArrayList;
import java.util.List;


public class DemeterDigitalPins implements DigitalPins {

    private List<DigitalIO> inputs = new ArrayList<>();
    private List<DigitalIO> relays = new ArrayList<>();

    public DemeterDigitalPins() {

        PeripheralManagerService service = new PeripheralManagerService();

        relays.add(new DemeterRelay(service, "BCM23")); // X3.8
        relays.add(new DemeterRelay(service, "BCM24")); // X3.7
        relays.add(new DemeterRelay(service, "BCM22")); // X3.6

        inputs.add(new DemeterInput(service, "BCM26")); // X4.5
        inputs.add(new DemeterInput(service, "BCM16"));  // X4.6

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
}
