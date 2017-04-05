package com.fiwio.iot.demeter;

import java.util.ArrayList;
import java.util.List;

public class DemeterRelays implements Relays {

    private static final String LOG_TAG = DemeterRelays.class.getSimpleName();

    private static final int NUM_RELAYS = 3;

    private List<Relay> relays = new ArrayList<>();

    public DemeterRelays() {
        relays.add(new DemeterRelay("BCM23")); // X3.8
        relays.add(new DemeterRelay("BCM24")); // X3.7
        relays.add(new DemeterRelay("BCM22")); // X3.6
    }


    @Override
    public void inverseSwitch(int index) {
        if (indexExists(index)) {
            Relay relay = relays.get(index);
            relay.setValue(relay.getValue() == DigitalOut.OFF ? DigitalOut.ON : DigitalOut.OFF);
        }
    }

    @Override
    public void setValue(int index, DigitalOut value) {
        relays.get(index).setValue(value == DigitalOut.OFF ? DigitalOut.ON : DigitalOut.OFF);
    }

    @Override
    public void setValue(String name, DigitalOut value) {
        for (Relay relay:relays) {
            if (relay.getName().equals(name)) {
                relay.setValue(value);
            }
        }
    }

    private boolean indexExists(int index) {
        return true;
    }

    @Override
    public DigitalOut getValue(int index) {
        if (indexExists(index)) {
            return relays.get(index).getValue();
        }
        return DigitalOut.OFF;
    }

    @Override
    public String getName(int index) {
        if (indexExists(index)) {
            return relays.get(index).getName();
        }
        return "UNKNOWN";
    }
}
