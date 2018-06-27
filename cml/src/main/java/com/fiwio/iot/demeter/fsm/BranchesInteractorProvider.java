package com.fiwio.iot.demeter.fsm;

public interface BranchesInteractorProvider {
    IOInteractor get(String branch);
}
