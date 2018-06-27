package com.martingregor;

import android.os.Handler;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.SpiDevice;

import java.io.IOException;

/**
 * Device driver for MCP23S17 chip on PiFace Digital 2
 */

public class PiFaceDigital2 implements AutoCloseable {
    private static final String TAG = PiFaceDigital2.class.getSimpleName();
    private static final String GPIO_PORT = "BCM25";
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static final byte IODIRA = 0x00;
    private static final byte IODIRB = 0x01;
    private static final byte IPOLA = 0x02;
    private static final byte IPOLB = 0x03;
    private static final byte GPINTENA = 0x04;
    private static final byte GPINTENB = 0x05;
    private static final byte DEFVALA = 0x06;
    private static final byte DEFVALB = 0x07;
    private static final byte INTCONA = 0x08;
    private static final byte INTCONB = 0x09;
    private static final byte IOCONA = 0x0A;
    private static final byte IOCONB = 0x0B;
    private static final byte GPPUA = 0x0C;
    private static final byte GPPUB = 0x0D;
    private static final byte INTFA = 0x0E;
    private static final byte INTFB = 0x0F;
    private static final byte INTCAPA = 0x10;
    private static final byte INTCAPB = 0x11;
    private static final byte GPIOA = 0x12;
    private static final byte GPIOB = 0x13;
    private static final byte OLATA = 0x14;
    private static final byte OLATB = 0x15;
    private static final byte BaseAddressWrite = 0x40;
    private static final byte BaseAddressRead = 0x41;

    private static byte[] readBuffer3 = new byte[3];
    private static byte[] writeBuffer3 = new byte[3];

    private final Handler mHandler;
    private final Runnable mRunnable;

    private final SpiDevice mSpiDevice;
    private final Gpio mGpio;

    private InputEdgeCallback mCallback;

    private GpioCallback mGpioCallback = new GpioCallback() {
        @Override
        public boolean onGpioEdge(Gpio gpio) {
            Log.i(TAG, "Interrupt occurred");

            try {
                readSpiDevice(INTFB);
                readSpiDevice(INTCAPB);

                byte[] array = new byte[]{readSpiDevice(GPIOB)};

                String currentButtonValue = bytesToHex(array);
                Log.d(TAG, currentButtonValue);

                if (mCallback != null) {
                    mCallback.onGpioEdge(readBuffer3);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        public void onGpioError(Gpio gpio, int error) {
            Log.w(TAG, gpio + ": Error event " + error);
        }
    };

    PiFaceDigital2(SpiDevice spiDevice, Gpio gpio, InputEdgeCallback callback) throws IOException {

        mCallback = callback;

        mGpio = gpio; // This pin is for detecting changes on output ports
        mGpio.setDirection(Gpio.DIRECTION_IN);
        mGpio.setActiveType(Gpio.ACTIVE_LOW);
        mGpio.setEdgeTriggerType(Gpio.EDGE_BOTH);
        mGpio.registerGpioCallback(mGpioCallback);

        mSpiDevice = spiDevice;
        mSpiDevice.setFrequency(10000000); // 10MHz
        mSpiDevice.setMode(SpiDevice.MODE0); // Mode 0 seems to work best for PiFaceDigital2
        mSpiDevice.setBitsPerWord(8);
        mSpiDevice.setBitJustification(SpiDevice.MODE0);

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    readSpiDevice(GPIOB);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mHandler.postDelayed(mRunnable, 1000);
            }
        };

        startSpiDevice();
    }

    private void startSpiDevice() throws IOException {
        writeSpiDevice(IOCONA, (byte) 0x28); // 28, 18 or 08, not sure about the difference

        writeSpiDevice(IODIRA, (byte) 0x00);
        writeSpiDevice(IODIRB, (byte) 0xFF);

        writeSpiDevice(GPPUA, (byte) 0x00);
        writeSpiDevice(GPPUB, (byte) 0xFF);

        writeSpiDevice(IPOLA, (byte) 0xFF);
        writeSpiDevice(IPOLB, (byte) 0x00);

        writeSpiDevice(DEFVALB, (byte) 0x00);
        writeSpiDevice(IOCONB, (byte) 0x00);
        writeSpiDevice(GPINTENB, (byte) 0xFF);
        writeSpiDevice(GPIOA, (byte) 0x00);

        mRunnable.run();
    }

    private void writeSpiDevice(byte address, byte data) throws IOException {
        if (mSpiDevice != null) {
            writeBuffer3[0] = BaseAddressWrite;
            writeBuffer3[1] = address;
            writeBuffer3[2] = data;
            mSpiDevice.write(writeBuffer3, writeBuffer3.length);
        }
    }

    private byte readSpiDevice(byte address) throws IOException {
        if (mSpiDevice != null) {
            writeBuffer3[0] = BaseAddressRead;
            writeBuffer3[1] = address;
            writeBuffer3[2] = 0;
            mSpiDevice.transfer(writeBuffer3, readBuffer3, writeBuffer3.length);

            // Log.d(TAG, "readSpiDevice - " + bytesToHex(readBuffer3));

            return readBuffer3[2];
        }
        return 0;
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Create a new driver for PiFace Digital 2.
     *
     * @param spiBusPort Name of the SPI bus
     */
    public static PiFaceDigital2 create(String spiBusPort, InputEdgeCallback callback) throws IOException {
        PeripheralManager peripheralManagerService = PeripheralManager.getInstance();
        try {
            return new PiFaceDigital2(peripheralManagerService.openSpiDevice(spiBusPort),
                    peripheralManagerService.openGpio(GPIO_PORT), callback);
        } catch (IOException e) {
            throw new IOException("Unable to open SPI device in bus port " + spiBusPort, e);
        }
    }


    /**
     * Turn on or off LED on specific position.
     *
     * @param position Position of the LED, value must be between 0 and 7
     * @param onOff    True to turn on, false to turn off
     */
    public void setLED(int position, boolean onOff) {
        setOutputPin(position, onOff);
    }

    /**
     * Set output pin on specific position on or off.
     *
     * @param position Position of the output pin, value must be between 0 and 7
     * @param onOff    True to turn on, false to turn off
     */
    public void setOutputPin(int position, boolean onOff) {
        if (position > 7 || position < 0) {
            Log.e(TAG, position + " is not a valid output pin / LED position");
        } else {
            try {
                byte LEDs = readSpiDevice(GPIOA);

                if (onOff) {
                    LEDs = (byte) (LEDs | (1 << position));
                } else {
                    LEDs = (byte) (LEDs & ~(1 << position));
                }

                writeSpiDevice(GPIOA, LEDs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Turn on or off relay on specific position.
     *
     * @param position Position of the relay, value must be either 0 or 1
     * @param onOff    True to turn on, false to turn off
     */
    public void setRelay(int position, boolean onOff) {
        if (position > 1 || position < 0) {
            Log.e(TAG, position + " is not a valid relay position");
        } else {
            setOutputPin(position, onOff);
        }
    }

    /**
     * Get bit values of input from the last read
     *
     * @param position Position of the relay, value must be either 0 or 1
     */
    public boolean getInput(int position) {
        if (position > 3 || position < 0) {
            Log.e(TAG, position + " is not a valid input position");
        } else {
            boolean result = ((readBuffer3[2] >> position) & 1) == 0;
           // Log.d(TAG, "result for pin=" + String.valueOf(position) + "=" + String.valueOf                    (result));
            return result;
        }
        return false;
    }

    /**
     * Releases the SPI interface.
     */
    @Override
    public void close() throws Exception {
        mGpio.unregisterGpioCallback(mGpioCallback);
        mGpio.close();
        mSpiDevice.close();
    }
}
