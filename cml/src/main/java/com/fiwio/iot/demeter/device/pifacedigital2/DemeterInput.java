package com.fiwio.iot.demeter.device.pifacedigital2;

import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalIOType;
import com.fiwio.iot.demeter.device.model.DigitalValue;
import com.martingregor.PiFaceDigital2;

public class DemeterInput implements DigitalIO {

    private static final String TAG = DemeterInput.class.getSimpleName();

    private final String name;
    private final int relayIndex;
    private final PiFaceDigital2 mPiFaceDigital2;


    public DemeterInput(final PiFaceDigital2 mPiFaceDigital2, final int relayIndex, String ioName) {
        this.name = ioName;
        this.relayIndex = relayIndex;
        this.mPiFaceDigital2 = mPiFaceDigital2;

    }

    @Override
    public void setValue(DigitalValue value) {
        // input is read only
    }

    @Override
    public DigitalValue getValue() {
        return mPiFaceDigital2.getInput(relayIndex) ? DigitalValue.ON : DigitalValue.OFF;
    }

    @Override
    public DigitalIOType getType() {
        return DigitalIOType.INPUT;
    }

    @Override
    public String getName() {
        return name;
    }
}