package com.fiwio.iot.demeter;

import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalPins;
import com.fiwio.iot.demeter.device.model.DigitalValue;
import com.fiwio.iot.demeter.fsm.IOInteractor;

import au.com.ds.ef.StatefulContext;

class DemeterIoInteractor implements IOInteractor {
  private final DigitalPins demeter;
  private final DigitalIO barrel_input;
  private final DigitalIO barrel_pump;
  private final DigitalIO branch_valve;

  public DemeterIoInteractor(DigitalPins demeter, String branchVentilName) {
    this.demeter = demeter;
    barrel_input = demeter.getInput("INP0");
    barrel_pump = demeter.getOutput("BCM23");
    branch_valve = demeter.getOutput(branchVentilName);
  }

  @Override
  public boolean swimmerIsInactive() {
    return barrel_input.getValue() == DigitalValue.ON;
  }

  @Override
  public void barrelPumpOn(StatefulContext context) {

    barrel_pump.setValue(DigitalValue.ON);
  }

  @Override
  public void openBarrel(StatefulContext context) {
    branch_valve.setValue(DigitalValue.ON);
  }

  @Override
  public void closeAllVentils(StatefulContext context) {
    barrel_pump.setValue(DigitalValue.OFF);
    branch_valve.setValue(DigitalValue.OFF);
  }
}
