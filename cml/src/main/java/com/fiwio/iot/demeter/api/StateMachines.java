package com.fiwio.iot.demeter.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateMachines {
    @SerializedName("fsm")
    @Expose
    private List<Fsm> fsm = null;

    public List<Fsm> getFsm() {
        return fsm;
    }

    public void setFsm(List<Fsm> fsm) {
        this.fsm = fsm;
    }
}
