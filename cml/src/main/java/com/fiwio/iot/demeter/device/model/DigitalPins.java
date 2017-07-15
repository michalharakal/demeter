package com.fiwio.iot.demeter.device.model;

import java.util.List;

public interface DigitalPins {
    
    List<DigitalIO> getInputs();

    List<DigitalIO> getOutputs();

    DigitalIO getInput(String name);

    DigitalIO getOutput(String name);

}
