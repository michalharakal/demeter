package com.fiwio.iot.demeter.domain.features.fsm;

import com.fiwio.iot.demeter.domain.features.tracking.EventTracker;
import com.fiwio.iot.demeter.domain.model.BranchValveParameters;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import au.com.ds.ef.StatefulContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GardenFiniteStateMachineTest {

  static Logger log = LoggerFactory.getLogger(GardenFiniteStateMachine.class);

  GardenFiniteStateMachine fsm;

  BranchValveParameters branchValveParameters;

  EventTracker tracker;

  SimpleIOInteractor ioInteractor;

  private List<String> events;

  @Before
  public void setUp() throws Exception {
    ioInteractor = new SimpleIOInteractor();
    branchValveParameters = new BranchValveParameters(1, 1, 1);
    events = new ArrayList<>();
    tracker =
        new EventTracker() {
          @Override
          public void track(@NotNull String event) {
            events.add(event);
            log.debug(event);
          }
        };
    fsm = new GardenFiniteStateMachine(tracker);
  }

  @Test
  public void noStartWithActiveSwimmer() throws InterruptedException {
    StatefulContext ctx = fsm.run("garden", branchValveParameters, ioInteractor);
    fsm.trigger(ctx, "fill");
    Thread.sleep(5000L);
    assertThat(fsm.getState(ctx), is(GardenFiniteStateMachine.States.CLOSED));
  }

  @Test
  public void noStartWithInactiveSwimmer() throws InterruptedException {
    BranchValveParameters branchValveParameters = new BranchValveParameters(2,2,2);
    StatefulContext ctx = fsm.run("flowers", branchValveParameters, ioInteractor);
    ioInteractor.swimmerActive = false;
    fsm.trigger(ctx, "fill");
    Thread.sleep(5000L);
    assertThat(fsm.getState(ctx), is(GardenFiniteStateMachine.States.CLOSED));
  }

  private class SimpleIOInteractor implements IOInteractor {

    private boolean swimmerActive = true;

    public boolean isSwimmer() {
      return swimmerActive;
    }

    public void setSwimmer(boolean swimmer) {
      this.swimmerActive = swimmer;
    }

    @Override
    public boolean swimmerIsInactive() {
      return swimmerActive;
    }

    @Override
    public void barrelPumpOn(StatefulContext context) {
      tracker.track("barrelPumpOn");
    }

    @Override
    public void openBarrel(StatefulContext context) {
      tracker.track("openBarrel");
    }

    @Override
    public void closeAllVentils(StatefulContext context) {
      tracker.track("closeAllVentils");
    }
  }
}
