package com.fiwio.iot.demeter.domain.features.fsm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SimpleCountDownTimer implements Runnable {

    static Logger log = LoggerFactory.getLogger(GardenFiniteStateMachine.class);


    private final long delay;

    protected SimpleCountDownTimer(long delay) {
        this.delay = delay;
    }

    @Override
    public void run() {
        try {
            log.debug("starting countdown" + String.valueOf(delay));
            Thread.sleep(delay);
            log.debug("countdown done");
        } catch (InterruptedException e) {
        }
        finished();
    }

    public abstract void finished();
}