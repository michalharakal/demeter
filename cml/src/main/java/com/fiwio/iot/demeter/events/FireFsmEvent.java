package com.fiwio.iot.demeter.events;

public class FireFsmEvent {

    public final String command;
    public final String fmsName;


    public FireFsmEvent(String fmsName, String command) {
        this.fmsName = fmsName;
        this.command = command;
    }
}
