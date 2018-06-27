package com.fiwio.iot.demeter.configuration;

public class BranchValveParameters {
    private long openingDurationSec;
    private long actionDurationSec;
    private long fillingDurationSec;

    public long getOpeningDurationSec() {
        return openingDurationSec;
    }

    public void setOpeningDurationSec(long openingDurationSec) {
        this.openingDurationSec = openingDurationSec;
    }

    public long getActionDurationSec() {
        return actionDurationSec;
    }

    public void setActionDurationSec(long actionDurationSec) {
        this.actionDurationSec = actionDurationSec;
    }

    public long getFillingDurationSec() {
        return fillingDurationSec;
    }

    public void setFillingDurationSec(long fillingDurationSec) {
        this.fillingDurationSec = fillingDurationSec;
    }
}
