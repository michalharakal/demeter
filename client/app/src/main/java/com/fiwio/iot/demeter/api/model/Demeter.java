package com.fiwio.iot.demeter.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Demeter
 */
public class Demeter {
    @SerializedName("relays")
    private List<Relay> relays = new ArrayList<Relay>();

    @SerializedName("inputs")
    private List<Input> inputs = new ArrayList<Input>();

    public Demeter relays(List<Relay> relays) {
        this.relays = relays;
        return this;
    }

    public Demeter addRelaysItem(Relay relaysItem) {
        this.relays.add(relaysItem);
        return this;
    }

    /**
     * Get relays
     *
     * @return relays
     **/
    public List<Relay> getRelays() {
        return relays;
    }

    public void setRelays(List<Relay> relays) {
        this.relays = relays;
    }

    public Demeter inputs(List<Input> inputs) {
        this.inputs = inputs;
        return this;
    }

    public Demeter addInputsItem(Input inputsItem) {
        this.inputs.add(inputsItem);
        return this;
    }

    /**
     * Get inputs
     *
     * @return inputs
     **/
    public List<Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Demeter demeter = (Demeter) o;

        if (relays != null ? !relays.equals(demeter.relays) : demeter.relays != null) return false;
        return inputs != null ? inputs.equals(demeter.inputs) : demeter.inputs == null;

    }

    @Override
    public int hashCode() {
        int result = relays != null ? relays.hashCode() : 0;
        result = 31 * result + (inputs != null ? inputs.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "com.fiwo.iot.demeter.api.api.model.Demeter{" +
                "inputs=" + inputs +
                ", relays=" + relays +
                '}';
    }
}

