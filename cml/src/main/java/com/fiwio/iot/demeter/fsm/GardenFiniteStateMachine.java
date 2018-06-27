package com.fiwio.iot.demeter.fsm;

import com.fiwio.iot.demeter.configuration.BranchValveParameters;
import com.fiwio.iot.demeter.events.FireFsmEvent;
import com.fiwio.iot.demeter.events.FireFsmStopEvent;
import com.fiwio.iot.demeter.tracking.EventTracker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.ds.ef.EasyFlow;
import au.com.ds.ef.EventEnum;
import au.com.ds.ef.FlowBuilder;
import au.com.ds.ef.StateEnum;
import au.com.ds.ef.StatefulContext;
import au.com.ds.ef.SyncExecutor;
import au.com.ds.ef.call.ContextHandler;
import au.com.ds.ef.err.LogicViolationError;

public class GardenFiniteStateMachine {

  public static final String BRANCH_GARDEN = "garden";
  public static final String BRANCH_FLOWERS = "flowers";
  public static final String BRANCH_GREENHOUSE = "greenhouse";

  private final EasyFlow<FlowContext> flower_flow;
  private final EventTracker tracker;
  private final Map<String, StatefulContext> branches = new HashMap<>();

  public States getState(StatefulContext ctx) {
    return (States) ctx.getState();
  }

  /**
   * iterate over possible commnad to find matching a triger event
   *
   * @param command
   */
  public void trigger(String branchName, String command) {

    if (command != null && (!"".equals(command))) {
      if (branches.containsKey(branchName)) {
        StatefulContext ctx = branches.get(branchName);
        for (Events event : Events.values()) {
          if (command.equals(event.getText())) {
            tracker.track("fired trigger=" + ctx.getId() + "-" + command + "<<<");
            try {
              flower_flow.trigger(event, (FlowContext) ctx);
            } catch (LogicViolationError logicViolationError) {
              tracker.track(
                  "error fired trigger="
                      + ctx.getId()
                      + "-"
                      + command
                      + ">>>"
                      + logicViolationError.getMessage());
            }
            tracker.track("fired trigger=" + ctx.getId() + "-" + command + ">>>");
          }
        }
      }
    }
  }

  public List<StatefulContext> getBranches() {
    return new ArrayList<>(branches.values());
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

  public GardenFiniteStateMachine(final EventTracker tracker) {
    this.tracker = tracker;

    EventBus.getDefault().register(this);

    flower_flow =
        FlowBuilder.from(States.CLOSED)
            .transit(
                // irrigating
                FlowBuilder.on(Events.irrigationStart)
                    .to(States.IRRIGATION_OPENING)
                    .transit(
                        FlowBuilder.on(Events.stop)
                            .to(States.CLOSING)
                            .transit(
                                FlowBuilder.on(Events.closingDurationLapsed).to(States.CLOSED)),
                        FlowBuilder.on(Events.openingIrrigationDurationLapsed)
                            .to(States.IRRIGATING)
                            .transit(
                                FlowBuilder.on(Events.irrigationDurationLapsed).to(States.CLOSING),
                                FlowBuilder.on(Events.stop).to(States.CLOSING))),
                // barrel filling
                FlowBuilder.on(Events.fillingStart)
                    .to(States.BARREL_FILLING_OPENING)
                    .transit(
                        FlowBuilder.on(Events.openingFillingDurationLapsed)
                            .to(States.BARREL_FILLING)
                            .transit(
                                FlowBuilder.on(Events.stop).to(States.CLOSING),
                                FlowBuilder.on(Events.barrelFull).to(States.CLOSING)),
                        FlowBuilder.on(Events.stop).to(States.CLOSING)));

    flower_flow.whenEnter(
        States.CLOSED,
        new ContextHandler<FlowContext>() {
          @Override
          public void call(final FlowContext context) throws Exception {
            context.ioInteractor.closeAllVentils(context);
          }
        });

    flower_flow.whenEnter(
        States.BARREL_FILLING_OPENING,
        new ContextHandler<FlowContext>() {
          @Override
          public void call(final FlowContext context) throws Exception {
            // only if mechanical flot not active
            if (context.ioInteractor.swimmerIsInactive()) {
              GardenFiniteStateMachine.this.tracker.track("swimmer inactive");

              context.ioInteractor.barrelPumpOn(context);

              long openingDurationMiliSec =
                  GardenFiniteStateMachine.intoMs(
                      context.branchValveParameters.getOpeningDurationSec());
              new Thread(
                      new SimpleCountDownTimer(openingDurationMiliSec) {
                        @Override
                        public void finished() {
                          GardenFiniteStateMachine.this.flower_flow.safeTrigger(
                              Events.openingFillingDurationLapsed, context);
                        }
                      })
                  .start();
            } else {
              GardenFiniteStateMachine.this.tracker.track("swimmer active, stopping");
              flower_flow.safeTrigger(Events.stop, context);
            }
          }
        });

    flower_flow.whenEnter(
        States.IRRIGATION_OPENING,
        new ContextHandler<FlowContext>() {

          @Override
          public void call(final FlowContext context) throws Exception {
            context.ioInteractor.openBarrel(context);

            long actionOpeningDuationMiliSec =
                GardenFiniteStateMachine.intoMs(
                    context.branchValveParameters.getOpeningDurationSec());
            new Thread(
                    new SimpleCountDownTimer(actionOpeningDuationMiliSec) {
                      @Override
                      public void finished() {
                        GardenFiniteStateMachine.this.flower_flow.safeTrigger(
                            Events.openingIrrigationDurationLapsed, context);
                      }
                    })
                .start();
          }
        });

    flower_flow.whenEnter(
        States.IRRIGATING,
        new ContextHandler<FlowContext>() {

          @Override
          public void call(final FlowContext context) throws Exception {
            long irrigationTimeMiliSec =
                GardenFiniteStateMachine.intoMs(
                    context.branchValveParameters.getActionDurationSec());
            new Thread(
                    new SimpleCountDownTimer(irrigationTimeMiliSec) {
                      @Override
                      public void finished() {
                        GardenFiniteStateMachine.this.flower_flow.safeTrigger(Events.stop, context);
                      }
                    })
                .start();
          }
        });

    flower_flow.whenEnter(
        States.BARREL_FILLING,
        new ContextHandler<FlowContext>() {

          @Override
          public void call(final FlowContext context) throws Exception {
            long fillingDurationMiliSec =
                GardenFiniteStateMachine.intoMs(
                    context.branchValveParameters.getFillingDurationSec());
            new Thread(
                    new SimpleCountDownTimer(fillingDurationMiliSec) {
                      @Override
                      public void finished() {
                        GardenFiniteStateMachine.this.flower_flow.safeTrigger(Events.stop, context);
                      }
                    })
                .start();
          }
        });

    flower_flow.whenEnter(
        States.CLOSING,
        new ContextHandler<FlowContext>() {
          @Override
          public void call(final FlowContext context) throws Exception {
            context.ioInteractor.closeAllVentils(context);
            long closingDurationMiliSec =
                GardenFiniteStateMachine.intoMs(
                    context.branchValveParameters.getOpeningDurationSec());
            new Thread(
                    new SimpleCountDownTimer(closingDurationMiliSec) {
                      @Override
                      public void finished() {
                        GardenFiniteStateMachine.this.flower_flow.safeTrigger(
                            Events.closingDurationLapsed, context);
                      }
                    })
                .start();
          }
        });
    flower_flow.trace();
  }

  private static long intoMs(long valueInSec) {
    return valueInSec * 1000;
  }

  @Subscribe(threadMode = ThreadMode.BACKGROUND)
  public void onFireFsmEvent(FireFsmEvent event) {
    if (branches.containsKey(event.fmsName)) {
      if (Events.stop.getText().equals(event.command)) {
        alwaysStop(event.fmsName);
        StatefulContext ctx = branches.get(event.fmsName);
        ((FlowContext) ctx).ioInteractor.closeAllVentils(ctx);
      }
      trigger(event.fmsName, event.command);
    }
  }

  @Subscribe(threadMode = ThreadMode.BACKGROUND)
  public void onFireFsmStopEvent(FireFsmStopEvent event) {
    if (branches.containsKey(event.fmsName)) {
      alwaysStop(event.fmsName);
    }
  }

  private void alwaysStop(String fmsName) {
    StatefulContext ctx = branches.get(fmsName);
    if (getState(ctx) != States.CLOSED) {
      trigger(fmsName, Events.stop.getText());
    } else {
      ((FlowContext) ctx).ioInteractor.closeAllVentils(ctx);
    }
  }

  public StatefulContext run(
      String id, BranchValveParameters branchValveParameters, IOInteractor ioInteractor) {

    StatefulContext ctx;
    if (!branches.containsKey(id)) {
      ctx = new FlowContext(id, branchValveParameters, ioInteractor);
      branches.put(id, ctx);
      flower_flow.trace().executor(new SyncExecutor()).start(ctx);
    } else {
      ctx = branches.get(id);
    }

    return ctx;
  }

  private static class FlowContext extends StatefulContext {

    private final BranchValveParameters branchValveParameters;
    private final IOInteractor ioInteractor;

    private FlowContext(
        String id, BranchValveParameters branchValveParameters, IOInteractor ioInteractor) {
      super(id);
      this.branchValveParameters = branchValveParameters;
      this.ioInteractor = ioInteractor;
    }
  }
}
