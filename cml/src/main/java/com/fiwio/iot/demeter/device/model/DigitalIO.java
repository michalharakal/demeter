package com.fiwio.iot.demeter.device.model;


public interface DigitalIO {

    void setValue(DigitalValue value);

    DigitalValue getValue();

    DigitalIOType getType();

    String getName();
}
