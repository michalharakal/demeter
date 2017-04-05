package com.fiwio.iot.demeter;

public interface Relays {

    void inverseSwitch(int index);

    void setValue(int index, DigitalOut value);

    void setValue(String name, DigitalOut value);

    // current relay value getter
    DigitalOut getValue(int index);

    String getName(int i);


}
