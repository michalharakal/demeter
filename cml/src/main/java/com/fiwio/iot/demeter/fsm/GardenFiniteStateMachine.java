package com.fiwio.iot.demeter.fsm;


import android.util.Log;

import com.fiwio.iot.demeter.configuration.ConfigurationProvider;
import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalValue;
import com.fiwio.iot.demeter.events.FireFsmEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.joda.time.DateTime;

import au.com.ds.ef.EasyFlow;
import au.com.ds.ef.EventEnum;
import au.com.ds.ef.StateEnum;
import au.com.ds.ef.StatefulContext;
import au.com.ds.ef.SyncExecutor;
import au.com.ds.ef.call.ContextHandler;

import static au.com.ds.ef.FlowBuilder.from;
import static au.com.ds.ef.FlowBuilder.on;


public class GardenFiniteStateMachine {

    private static final String TAG = GardenFiniteStateMachine.class.getSimpleName();

    private final EasyFlow<FlowContext> flower_flow;
    private final DigitalIO barrel_pump;
    private final DigitalIO barrel_input;
    private final DigitalIO barrel_valve;

    private FlowContext ctx;

    private final ConfigurationProvider configurationProvider;

    public States getState() {
        return (States) ctx.getState();
    }

    /**
     * iterate over possible commnad to find matching a triger event
     *
     * @param command
     */
    public void trigger(String command) {

        if (command != null && (!"".equals(command))) {
            for (Events event : Events.values()) {
                if (command.equals(event.getText())) {
                    flower_flow.safeTrigger(event, ctx);
                    Log.d(TAG, "fired trigger=" + command);
                }
            }
        }
    }

    public String getName() {
        return "garden";
    }

    public enum States implements StateEnum {
        CLOSED("CLOSED"),
        IRRIGATION_OPENING("IRRIGATION_OPENING"),
        IRRIGATING("IRRIGATING"),
        BARREL_FILLING_OPENING("BARREL_FILLING_OPENING"),
        BARREL_FILLING("BARREL_FILLING"),
        CLOSING("CLOSING");

        private final String fieldDescription;

        States(String value) {
            fieldDescription = value;
        }

        public String getText() {
            return fieldDescription;
        }
    }

    public enum Events implements EventEnum {
        irrigationStart("irrigate"),
        fillingStart("fill"),
        stop("stop"),
        openingIrrigationDurationLapsed("openingIrrigationDurationLapsed"),
        irrigationDurationLapsed("irrigationDurationLapsed"),
        openingFillingDurationLapsed("openingFillingDurationLapsed"),
        barrelFull("barrelFull"),
        closingDurationLapsed("closingDurationLapsed");

        private final String fieldDescription;

        Events(String value) {
            fieldDescription = value;
        }

        public String getText() {
            return fieldDescription;
        }
    }

    public GardenFiniteStateMachine(final DigitalIO barrel_pump, DigitalIO barrel_valve, final DigitalIO barrel_input, ConfigurationProvider configurationProvider) {
        this.barrel_input = barrel_input;
        this.barrel_pump = barrel_pump;
        this.barrel_valve = barrel_valve;
        this.configurationProvider = configurationProvider;

        EventBus.getDefault().register(this);

        flower_flow =
                from(States.CLOSED).transit(
                        // irrigating
                        on(Events.irrigationStart).to(States.IRRIGATION_OPENING).transit(
                                on(Events.stop).to(States.CLOSING).transit(
                                        on(Events.closingDurationLapsed).to(States.CLOSED)
                                ),
                                on(Events.openingIrrigationDurationLapsed).to(States.IRRIGATING).transit(
                                        on(Events.irrigationDurationLapsed).to(States.CLOSING),
                                        on(Events.stop).to(States.CLOSING)
                                )
                        ),
                        // barrel filling
                        on(Events.fillingStart).to(States.BARREL_FILLING_OPENING).transit(
                                on(Events.openingFillingDurationLapsed).to(States.BARREL_FILLING).transit(
                                        on(Events.stop).to(States.CLOSING),
                                        on(Events.barrelFull).to(States.CLOSING)
                                ),
                                on(Events.stop).to(States.CLOSING)
                        )
                );


        flower_flow
                .whenEnter(States.CLOSED, new ContextHandler<FlowContext>() {
                    @Override
                    public void call(final FlowContext context) throws Exception {
                        closeAllVentils();
                    }
                });

        flower_flow
                .whenEnter(States.BARREL_FILLING_OPENING, new ContextHandler<FlowContext>() {
                    @Override
                    public void call(final FlowContext context) throws Exception {
                        // only if mechanical flot not active
                        if (GardenFiniteStateMachine.this.barrel_input.getValue() == DigitalValue.ON) {
                            GardenFiniteStateMachine.this.barrel_pump.setValue(DigitalValue.ON);

                            new Thread(new SimpleCountDownTimer(GardenFiniteStateMachine.this.intoMs(GardenFiniteStateMachine
                                    .this.configurationProvider.getValveOpeningDuration())) {
                                @Override
                                public void finished() {
                                    GardenFiniteStateMachine.this.flower_flow.safeTrigger(Events.openingFillingDurationLapsed, context);
                                }
                            }).start();
                        } else {
                            flower_flow.safeTrigger(Events.stop, ctx);
                        }
                    }
                });

        flower_flow
                .whenEnter(States.IRRIGATION_OPENING, new ContextHandler<FlowContext>()

                {
                    @Override
                    public void call(final FlowContext context) throws Exception {
                        GardenFiniteStateMachine.this.barrel_valve.setValue(DigitalValue.ON);

                        new Thread(new SimpleCountDownTimer(GardenFiniteStateMachine.this.intoMs(
                                GardenFiniteStateMachine.this
                                        .configurationProvider.getValveOpeningDuration())) {
                            @Override
                            public void finished() {
                                GardenFiniteStateMachine.this.flower_flow.safeTrigger(Events.openingIrrigationDurationLapsed, context);
                            }
                        }).start();
                    }
                });

        flower_flow
                .whenEnter(States.IRRIGATING, new ContextHandler<FlowContext>()

                {
                    @Override
                    public void call(final FlowContext context) throws Exception {
                        new Thread(new SimpleCountDownTimer(GardenFiniteStateMachine.this.intoMs(GardenFiniteStateMachine.this
                                .configurationProvider.getIrrigatingDuration())) {
                            @Override
                            public void finished() {
                                GardenFiniteStateMachine.this.flower_flow.safeTrigger(Events.stop, context);
                            }
                        }).start();
                    }
                });

        flower_flow
                .whenEnter(States.BARREL_FILLING, new ContextHandler<FlowContext>()

                {
                    @Override
                    public void call(final FlowContext context) throws Exception {
                        new Thread(new SimpleCountDownTimer(GardenFiniteStateMachine.this.intoMs(GardenFiniteStateMachine
                                .this.configurationProvider.getBarrelFillingDuration())) {
                            @Override
                            public void finished() {
                                GardenFiniteStateMachine.this.flower_flow.safeTrigger(Events.stop, context);
                            }
                        }).start();

                    }
                });


        flower_flow
                .whenEnter(States.CLOSING, new ContextHandler<FlowContext>() {
                    @Override
                    public void call(final FlowContext context) throws Exception {
                        closeAllVentils();
                        new Thread(new SimpleCountDownTimer(GardenFiniteStateMachine.this.intoMs(GardenFiniteStateMachine.this
                                .configurationProvider.getValveOpeningDuration())) {
                            @Override
                            public void finished() {
                                GardenFiniteStateMachine.this.flower_flow.safeTrigger(Events.closingDurationLapsed, context);
                            }
                        }).start();

                    }
                });
        flower_flow.trace();
    }

    private long intoMs(long valueInSec) {
        return valueInSec * 1000;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onFireFsmEvent(FireFsmEvent event) {
        trigger(event.command);
    }

    public void run() {
        ctx = new FlowContext();

        flower_flow
                .trace()
                .executor(new SyncExecutor())
                .start(ctx);

    }

    private void closeAllVentils() {
        GardenFiniteStateMachine.this.barrel_pump.setValue(DigitalValue.OFF);
        GardenFiniteStateMachine.this.barrel_valve.setValue(DigitalValue.OFF);
    }

    private static class FlowContext extends StatefulContext {
        private DateTime irrigationTime;
        private int OpeningDuration = 1000;
    }
}
