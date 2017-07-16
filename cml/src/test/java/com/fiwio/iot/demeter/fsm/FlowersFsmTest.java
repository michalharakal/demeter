package com.fiwio.iot.demeter.fsm;

import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalValue;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class FlowersFsmTest {

    @Mock
    DigitalIO barrel_pump;

    @Mock
    DigitalIO barrel_valve;

    @Test
    public void valvesClosedOnStart() {
        FlowersFsm fsm = new FlowersFsm(barrel_pump, barrel_valve, 10, 10, 10);
        fsm.run();
        Assert.assertThat(fsm.getState(), is(equalTo(FlowersFsm.States.CLOSED)));
        verify(barrel_pump).setValue(DigitalValue.OFF);
    }

    @Test
    public void irrigationWorking() {
        FlowersFsm fsm = new FlowersFsm(barrel_pump, barrel_valve, 10, 10, 10);
        fsm.run();
        fsm.irrigate();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        verify(barrel_pump, times(3)).setValue(DigitalValue.OFF);
        verify(barrel_valve, times(3)).setValue(DigitalValue.OFF);
        verify(barrel_valve, times(1)).setValue(DigitalValue.ON);
    }

    @Test
    public void fillingWorking() {
        FlowersFsm fsm = new FlowersFsm(barrel_pump, barrel_valve, 10, 10, 10);
        fsm.run();
        fsm.fillBarrel();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        verify(barrel_pump, times(3)).setValue(DigitalValue.OFF);
        verify(barrel_pump, times(1)).setValue(DigitalValue.ON);
    }


}