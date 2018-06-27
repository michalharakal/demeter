package com.fiwio.iot.demeter.fsm;

import com.fiwio.iot.demeter.configuration.ConfigurationProvider;

public class FsmRunnable implements Runnable {
  private static Thread _instance;

  private final GardenFiniteStateMachine fsm;
  private final ConfigurationProvider provider;
  private final BranchesInteractorProvider branchInteractorProvider;

  private FsmRunnable(
      GardenFiniteStateMachine fsm,
      ConfigurationProvider provider,
      BranchesInteractorProvider branchInteractorProvider) {
    this.fsm = fsm;
    this.provider = provider;
    this.branchInteractorProvider = branchInteractorProvider;
  }

  public static synchronized Thread getInstance(
      GardenFiniteStateMachine fsm,
      ConfigurationProvider provider,
      BranchesInteractorProvider interactorProvider) {
    if (_instance == null) {
      _instance = new Thread(new FsmRunnable(fsm, provider, interactorProvider));
    }
    return _instance;
  }

  @Override
  public void run() {
    FsmBrachnesFactory.startBranches(provider, fsm, branchInteractorProvider);
  }
}
