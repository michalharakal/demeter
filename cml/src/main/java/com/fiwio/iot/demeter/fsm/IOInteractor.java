package com.fiwio.iot.demeter.fsm;

import au.com.ds.ef.StatefulContext;

public interface IOInteractor {
    boolean swimmerIsInactive();

    void barrelPumpOn(StatefulContext context);

    void openBarrel(StatefulContext context);

    void closeAllVentils(StatefulContext context);
}
