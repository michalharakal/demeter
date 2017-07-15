package com.fiwio.iot.demeter.fsm;


import com.fiwio.iot.demeter.device.model.DigitalIO;
import com.fiwio.iot.demeter.device.model.DigitalValue;

import org.joda.time.DateTime;

import au.com.ds.ef.EasyFlow;
import au.com.ds.ef.EventEnum;
import au.com.ds.ef.StateEnum;
import au.com.ds.ef.StatefulContext;
import au.com.ds.ef.SyncExecutor;
import au.com.ds.ef.call.ContextHandler;

import static au.com.ds.ef.FlowBuilder.from;
import static au.com.ds.ef.FlowBuilder.on;


public class FlowersFsm {

    private static final String TAG = FlowersFsm.class.getSimpleName();


    private static final long TEN_MINUTES_IN_MS = 10 * 60 * 1000;
    private static final long TWENTY_MINUTES_IN_MS = 2 * TEN_MINUTES_IN_MS;
    private static final long THIRTY_SECONDS_IN_MS = 30 * 1000;

    private final EasyFlow<FlowContext> flower_flow;
    private final DigitalIO barrel_pump;
    private final DigitalIO barrel_valve;

    private FlowContext ctx;

    private final long valveOpeningDuration;
    private final long irrigatingDuration;
    private final long barrelFillingDuration;

    public States getState() {
        return (States) ctx.getState();
    }

    public void irrigate() {
        flower_flow.safeTrigger(Events.irrigationStart, ctx);
    }

    public void fillBarrel() {
        flower_flow.safeTrigger(Events.fillingStart, ctx);
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

    enum Events implements EventEnum {
        irrigationStart,
        fillingStart,
        stop,
        openingIrrigationDurationLapsed,
        irrigationDurationLapsed,
        openingFillingDurationLapsed,
        barrelFull,
        closingDurationLapsed
    }

    public FlowersFsm(final DigitalIO barrel_pump, DigitalIO barrel_valve) {
        this(barrel_pump, barrel_valve, THIRTY_SECONDS_IN_MS, TEN_MINUTES_IN_MS, TWENTY_MINUTES_IN_MS);
    }

    public FlowersFsm(final DigitalIO barrel_pump, DigitalIO barrel_valve, long irrigatingDuration) {
        this(barrel_pump, barrel_valve, THIRTY_SECONDS_IN_MS, irrigatingDuration, TWENTY_MINUTES_IN_MS);
    }

    // TODO replace with builder
    public FlowersFsm(final DigitalIO barrel_pump, DigitalIO barrel_valve, final long
            valveOpeningDuration, long irrigatingDuration, long barrelFillingDuration) {
        this.barrel_pump = barrel_pump;
        this.barrel_valve = barrel_valve;

        this.valveOpeningDuration = valveOpeningDuration;
        this.irrigatingDuration = irrigatingDuration;
        this.barrelFillingDuration = barrelFillingDuration;


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
                                        on(Events.barrelFull).to(States.CLOSING).transit(
                                                on(Events.closingDurationLapsed).to(States.CLOSED)
                                        )
                                )
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
                        FlowersFsm.this.barrel_pump.setValue(DigitalValue.ON);
                        Thread.sleep(FlowersFsm.this.valveOpeningDuration);
                        FlowersFsm.this.flower_flow.trigger(Events.openingFillingDurationLapsed, context);
                    }
                });

        flower_flow
                .whenEnter(States.IRRIGATION_OPENING, new ContextHandler<FlowContext>()

                {
                    @Override
                    public void call(final FlowContext context) throws Exception {
                        FlowersFsm.this.barrel_valve.setValue(DigitalValue.ON);
                        Thread.sleep(FlowersFsm.this.valveOpeningDuration);
                        FlowersFsm.this.flower_flow.trigger(Events.openingIrrigationDurationLapsed, context);

                    }
                });

        flower_flow
                .whenEnter(States.IRRIGATING, new ContextHandler<FlowContext>()

                {
                    @Override
                    public void call(final FlowContext context) throws Exception {
                        Thread.sleep(FlowersFsm.this.irrigatingDuration);
                        FlowersFsm.this.flower_flow.trigger(Events.stop, context);
                    }
                });

        flower_flow
                .whenEnter(States.BARREL_FILLING, new ContextHandler<FlowContext>()

                {
                    @Override
                    public void call(final FlowContext context) throws Exception {
                        Thread.sleep(FlowersFsm.this.barrelFillingDuration);
                        FlowersFsm.this.flower_flow.trigger(Events.stop, context);
                    }
                });


        flower_flow
                .whenEnter(States.CLOSING, new ContextHandler<FlowContext>() {
                    @Override
                    public void call(final FlowContext context) throws Exception {
                        closeAllVentils();
                        Thread.sleep(FlowersFsm.this.valveOpeningDuration);
                        FlowersFsm.this.flower_flow.trigger(Events.openingIrrigationDurationLapsed, context);

                    }
                });
    }

    public void run() {
        ctx = new FlowContext();

        flower_flow
                .trace()
                .executor(new SyncExecutor())
                .start(ctx);

    }

    private void closeAllVentils() {
        FlowersFsm.this.barrel_pump.setValue(DigitalValue.OFF);
        FlowersFsm.this.barrel_valve.setValue(DigitalValue.OFF);
    }

    private static class FlowContext extends StatefulContext {
        private DateTime irrigationTime;
        private int OpeningDuration = 1000;
    }
}
