package com.fiwio.iot.demeter.fsm;

public abstract class SimpleCountDownTimer implements Runnable {

    private final long delay;

    protected SimpleCountDownTimer(long delay) {
        this.delay = delay;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
        finished();
    }

    public abstract void finished();
}