package com.fiwio.iot.demeter.configuration;

import com.fiwio.iot.demeter.fsm.GardenFiniteStateMachine;

import java.util.HashMap;
import java.util.Map;

public class DemeterConfigurationProvider implements ConfigurationProvider {

  private static final long FIVE_MINUTES_IN_SECS = 5 * 60;
  private static final long THIRTY_MINUTES_IN_SECS = 30 * 60;
  private static final long TWENTY_SECS = 20;

  private final Configuration configuration;
  private final Map<String, BranchValveParameters> branches = new HashMap<>();

  public DemeterConfigurationProvider(Configuration configuration) {
    this.configuration = configuration;
    createGarden();
    createFlowers();
    createGreenhouse();
  }

  private void createGarden() {
    BranchValveParameters garden = new BranchValveParameters();
    garden.setActionDurationSec(FIVE_MINUTES_IN_SECS);
    garden.setFillingDurationSec(THIRTY_MINUTES_IN_SECS);
    garden.setOpeningDurationSec(TWENTY_SECS);
    branches.put(GardenFiniteStateMachine.BRANCH_GARDEN, garden);
  }

  private void createFlowers() {
    BranchValveParameters garden = new BranchValveParameters();
    garden.setActionDurationSec(FIVE_MINUTES_IN_SECS);
    garden.setFillingDurationSec(THIRTY_MINUTES_IN_SECS);
    garden.setOpeningDurationSec(TWENTY_SECS);
    branches.put(GardenFiniteStateMachine.BRANCH_FLOWERS, garden);
  }

  private void createGreenhouse() {
    BranchValveParameters garden = new BranchValveParameters();
    garden.setActionDurationSec(FIVE_MINUTES_IN_SECS);
    garden.setFillingDurationSec(THIRTY_MINUTES_IN_SECS);
    garden.setOpeningDurationSec(TWENTY_SECS);
    branches.put(GardenFiniteStateMachine.BRANCH_GREENHOUSE, garden);
  }


  @Override
  public BranchValveParameters getBranchParameters(String branch) {
    return branches.get(branch);
  }
}
