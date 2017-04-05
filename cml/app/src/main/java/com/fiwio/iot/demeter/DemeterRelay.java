package com.fiwio.iot.demeter;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

/**
 * Created by mharakal on 04.04.17.
 */

public class DemeterRelay implements Relay {

    private static final String TAG = DemeterRelay.class.getSimpleName();
    private Gpio mLedGpio;
    private final String name;

    public DemeterRelay(String ioName) {
        this.name = ioName;
        // Step 1. Create GPIO connection.
        PeripheralManagerService service = new PeripheralManagerService();
        try {
            mLedGpio = service.openGpio(ioName);
            // Step 2. Configure as an output.
            mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }
    }

    @Override
    public void setValue(DigitalOut value) {
        // Step 3. Toggle the LED state
        try {
            mLedGpio.setValue(value == DigitalOut.ON ? true : false);
        } catch (IOException e) {
            Log.e(TAG, "error setting Relay " + name);
        }

    }

    @Override
    public DigitalOut getValue() {
        try {
            return mLedGpio.getValue() ? DigitalOut.ON : DigitalOut.OFF;
        } catch (IOException e) {
            Log.e(TAG, "error getting Relay " + name);
        }
        return DigitalOut.OFF;
    }

    @Override
    public String getName() {
        return name;
    }
}
