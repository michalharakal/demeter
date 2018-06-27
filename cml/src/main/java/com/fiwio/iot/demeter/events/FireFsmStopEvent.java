package com.fiwio.iot.demeter.events;

public class FireFsmStopEvent {
    public final String fmsName;

    public FireFsmStopEvent(String fmsName) {
        this.fmsName = fmsName;
    }

}
