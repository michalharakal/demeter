package com.fiwio.iot.demeter.device.model;

public interface DigitalIoCallback {

    boolean onGpioEdge(DigitalIO digitalIO);

    void onGpioError(DigitalIO gpio, int error);
}
