package com.fiwio.iot.demeter.fsm;


public class FsmRunnable implements Runnable {
    private static final String LOG_TAG = FsmRunnable.class.getSimpleName();

    private static Thread _instance;

    private final GardenFiniteStateMachine fsm;


    private FsmRunnable(GardenFiniteStateMachine fsm) {
        this.fsm = fsm;
    }


    public synchronized static Thread getInstance(GardenFiniteStateMachine fsm) {
        if (_instance == null) {
            _instance = new Thread(new FsmRunnable(fsm));
        }
        return _instance;
    }

    @Override
    public void run() {
        fsm.run();
    }
}