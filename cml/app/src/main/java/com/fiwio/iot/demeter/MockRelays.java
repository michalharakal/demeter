package com.fiwio.iot.demeter;

import java.util.ArrayList;
import java.util.List;

class MockRelays implements Relays {

    private static final String TAG = MockRelays.class.getSimpleName();

    private List<Relay> relays = new ArrayList<>(3);


    @Override
    public void inverseSwitch(int index) {

    }

    @Override
    public void setValue(int index, DigitalOut value) {

    }

    @Override
    public void setValue(String name, DigitalOut value) {

    }

    @Override
    public DigitalOut getValue(int index) {
        return null;
    }

    @Override
    public String getName(int i) {
        return null;
    }
}
