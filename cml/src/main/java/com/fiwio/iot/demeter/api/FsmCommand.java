package com.fiwio.iot.demeter.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public class FsmCommand {

    @SerializedName("fsm")
    @Expose
    private String fsm;

    @SerializedName("command")
    @Expose
    private String command;

    @SerializedName("time")
    @Expose
    private DateTime time;

    public String getFsm() {
        return fsm;
    }

    public void setFsm(String fsm) {
        this.fsm = fsm;
    }

    public String getCommnad() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }
}
