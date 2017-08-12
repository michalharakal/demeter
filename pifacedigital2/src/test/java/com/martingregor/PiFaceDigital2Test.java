package com.martingregor;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.SpiDevice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class PiFaceDigital2Test implements InputEdgeCallback {
	@Mock
	private SpiDevice mSpiDevice;
	@Mock
	private Gpio mGpio;

	private PiFaceDigital2 mPiFaceDigital2;

	@Before
	public void setUp() throws IOException {
		mPiFaceDigital2 = new PiFaceDigital2(mSpiDevice, mGpio, this);
	}

	@Test
	public void write() throws IOException {
		mPiFaceDigital2.setLED(0, true);

		//verify(mSpiDevice).write(any(byte[].class), anyInt());
	}

	@Override
	public boolean onGpioEdge(byte[] values) {
		return false;
	}
}