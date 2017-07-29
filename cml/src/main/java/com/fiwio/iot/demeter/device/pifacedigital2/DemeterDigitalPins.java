package com.fiwio.iot.demeter.device.pifacedigital2;

import com.fiwio.iot.demeter.device.BoardDefaults;
import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalPins;
import com.martingregor.PiFaceDigital2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DemeterDigitalPins implements DigitalPins {

    private PiFaceDigital2 piFaceDigital2;
    private List<DigitalIO> inputs = new ArrayList<>();
    private List<DigitalIO> relays = new ArrayList<>();

    public DemeterDigitalPins() {

        try {
            piFaceDigital2 = PiFaceDigital2.create(BoardDefaults.getSPIPort());
            relays.add(new DemeterRelay(piFaceDigital2, 7, "BCM23")); // Barrel filling
            relays.add(new DemeterRelay(piFaceDigital2, 6, "BCM24")); // irrigating
            relays.add(new DemeterRelay(piFaceDigital2, 5, "BCM22")); // flowers

            inputs.add(new DemeterInput(piFaceDigital2, 0, "INP0")); // flowers
            inputs.add(new DemeterInput(piFaceDigital2, 1, "INP1")); // flowers


        } catch (IOException e) {
            e.printStackTrace();
        }
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