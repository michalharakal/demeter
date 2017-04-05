package com.fiwio.iot.demeter;


interface Relay {

    void setValue(DigitalOut value);

    DigitalOut getValue();

    String getName();
}
