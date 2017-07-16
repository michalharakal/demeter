package com.fiwio.iot.demeter.device.rpi3;

import android.util.Log;

import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalIOType;
import com.fiwio.iot.demeter.device.model.DigitalValue;
import com.fiwio.iot.demeter.events.FireFsmEvent;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManagerService;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class DemeterInput implements DigitalIO {

    private static final String TAG = DemeterInput.class.getSimpleName();
    private Gpio mLedGpio;
    private final String name;

    public DemeterInput(PeripheralManagerService gpio, String ioName) {
        this.name = ioName;
        try {
            mLedGpio = gpio.openGpio(ioName);
            mLedGpio.setDirection(Gpio.DIRECTION_IN);
            mLedGpio.setActiveType(Gpio.ACTIVE_LOW);

            // Register for all state changes
            mLedGpio.setEdgeTriggerType(Gpio.EDGE_BOTH);
            mLedGpio.registerGpioCallback(mGpioCallback);

        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }
    }

    private GpioCallback mGpioCallback = new GpioCallback() {
        @Override
        public boolean onGpioEdge(Gpio gpio) {
            // Read the active low pin state
            try {
                if (mLedGpio.getValue()) {
                    EventBus.getDefault().post(new FireFsmEvent("stop", "garden"));
                } else {
                    // Pin is HIGH
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Continue listening for more interrupts
            return true;
        }

        @Override
        public void onGpioError(Gpio gpio, int error) {
            Log.w(TAG, gpio + ": Error event " + error);
        }
    };

    @Override
    public void setValue(DigitalValue value) {
        // input is read only
    }

    @Override
    public DigitalValue getValue() {
        try {
            return mLedGpio.getValue() ? DigitalValue.ON : DigitalValue.OFF;
        } catch (IOException e) {
            return DigitalValue.OFF;
        }
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
