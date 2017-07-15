
package com.fiwio.iot.demeter.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Demeter {

    @SerializedName("relays")
    @Expose
    private List<Relay> relays = null;
    @SerializedName("inputs")
    @Expose
    private List<Input> inputs = null;

    public List<Relay> getRelays() {
        return relays;
    }

    public void setRelays(List<Relay> relays) {
        this.relays = relays;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }

}
