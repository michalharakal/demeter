package com.fiwio.iot.demeter.device.model;

public interface Relays {

    void inverseSwitch(int index);

    void setValue(int index, DigitalValue value);

    void setValue(String name, DigitalValue value);

    // current relay value getter
    DigitalValue getValue(int index);

    String getName(int i);


}
