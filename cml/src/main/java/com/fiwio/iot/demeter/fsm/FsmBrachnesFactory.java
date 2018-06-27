package com.fiwio.iot.demeter.fsm;

import com.fiwio.iot.demeter.configuration.ConfigurationProvider;

public class FsmBrachnesFactory {
  public static void startBranches(
      ConfigurationProvider configurationProvider,
      GardenFiniteStateMachine fsm,
      BranchesInteractorProvider interactorProvider) {
    runBranch(
        GardenFiniteStateMachine.BRANCH_GARDEN, configurationProvider, fsm, interactorProvider);
    runBranch(
        GardenFiniteStateMachine.BRANCH_FLOWERS, configurationProvider, fsm, interactorProvider);
    runBranch(
        GardenFiniteStateMachine.BRANCH_GREENHOUSE, configurationProvider, fsm, interactorProvider);
  }

  private static void runBranch(
      String branch,
      ConfigurationProvider configurationProvider,
      GardenFiniteStateMachine fsm,
      BranchesInteractorProvider interactorProvider) {
    fsm.run(
        branch, configurationProvider.getBranchParameters(branch), interactorProvider.get(branch));
  }
}
