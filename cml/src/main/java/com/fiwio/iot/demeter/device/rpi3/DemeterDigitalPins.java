package com.fiwio.iot.demeter.device.rpi3;

import android.util.Log;

import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalIoCallback;
import com.fiwio.iot.demeter.device.model.DigitalPins;
import com.fiwio.iot.demeter.device.model.DigitalValue;
import com.fiwio.iot.demeter.events.FireFsmEvent;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManagerService;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class DemeterDigitalPins implements DigitalPins, DigitalIoCallback {

    private static final String TAG = DemeterDigitalPins.class.getSimpleName();
    private List<DigitalIO> inputs = new ArrayList<>();
    private List<DigitalIO> relays = new ArrayList<>();

    public DemeterDigitalPins() {

        PeripheralManagerService service = new PeripheralManagerService();

        relays.add(new DemeterRelay(service, "BCM23")); // X3.8 barrel filling
        relays.add(new DemeterRelay(service, "BCM24")); // X3.7 irrigating
        relays.add(new DemeterRelay(service, "BCM22")); // X3.6

        inputs.add(new DemeterInput(service, "BCM26", this)); // X4.5
        inputs.add(new DemeterInput(service, "BCM16", this));  // X4.6

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
    public boolean onGpioEdge(DigitalIO digitalIO) {
        if (digitalIO.getValue() == DigitalValue.ON) {
            EventBus.getDefault().post(new FireFsmEvent("garden", "stop"));
        }
        return true;
    }

    @Override
    public void onGpioError(DigitalIO gpio, int error) {

    }
}
