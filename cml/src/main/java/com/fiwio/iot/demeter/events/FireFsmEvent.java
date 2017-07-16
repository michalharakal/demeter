package com.fiwio.iot.demeter.events;

public class FireFsmEvent {

    public final String command;
    public final String name;


    public FireFsmEvent(String command, String name) {
        this.command = command;
        this.name = name;
    }
}
