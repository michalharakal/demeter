package com.fiwio.iot.demeter.device.pifacedigital2;

import android.util.Log;

import com.fiwio.iot.demeter.device.BoardDefaults;
import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalIoCallback;
import com.fiwio.iot.demeter.device.model.DigitalPins;
import com.fiwio.iot.demeter.device.model.DigitalValue;
import com.fiwio.iot.demeter.events.FireFsmEvent;
import com.fiwio.iot.demeter.fsm.GardenFiniteStateMachine;
import com.martingregor.InputEdgeCallback;
import com.martingregor.PiFaceDigital2;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DemeterDigitalPins implements DigitalPins, InputEdgeCallback {

    private static final String LOG_TAG = DemeterDigitalPins.class.getSimpleName();
    private final DemeterInput floatBarrel;
    private final PiFaceDigital2 piFaceDigital2;
    private final GardenFiniteStateMachine fsm;
    private List<DigitalIO> inputs = new ArrayList<>();
    private List<DigitalIO> relays = new ArrayList<>();

    public DemeterDigitalPins(GardenFiniteStateMachine fsm) throws IOException {
        this.fsm = fsm;

        piFaceDigital2 = PiFaceDigital2.create(BoardDefaults.getSPIPort(), this);
        relays.add(new DemeterRelay(piFaceDigital2, 0, "BCM23")); // Barrel filling
        relays.add(new DemeterRelay(piFaceDigital2, 1, "BCM24")); // irrigating

        floatBarrel = new DemeterInput(piFaceDigital2, 0, "INP0"); // mechanical float - barrel
        inputs.add(floatBarrel);

        //    inputs.add(new DemeterInput(piFaceDigital2, 1, "INP1")); // flowers ???

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

    @Override
    public boolean onGpioEdge(byte[] values) {

        if (floatBarrel.getValue() == DigitalValue.OFF) {
            Log.d(LOG_TAG, "triggered on value =" + ((floatBarrel.getValue() == DigitalValue.OFF) ? "OFF" : "ON"));
            EventBus.getDefault().post(new FireFsmEvent("garden", "stop"));
        }
        return true;

    }
}