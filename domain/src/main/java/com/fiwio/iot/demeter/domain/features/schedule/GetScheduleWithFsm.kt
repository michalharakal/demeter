package com.fiwio.iot.demeter.domain.features.schedule

import com.fiwio.iot.demeter.domain.executor.PostExecutionThread
import com.fiwio.iot.demeter.domain.executor.ThreadExecutor
import com.fiwio.iot.demeter.domain.interactor.ObservableUseCase
import com.fiwio.iot.demeter.domain.model.FsmList
import com.fiwio.iot.demeter.domain.model.ScheduledActions
import com.fiwio.iot.demeter.domain.model.ScheduledActionsWithState
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetScheduleWithFsm @Inject constructor(val demeterRepository: DemeterRepository,
                                             threadExecutor: ThreadExecutor,
                                             postExecutionThread: PostExecutionThread) :
        ObservableUseCase<ScheduledActionsWithState, Void?>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Void?): Observable<ScheduledActionsWithState> {
        val scheduledActions = demeterRepository.getSchedules()
        val fsm = demeterRepository.getFsm()
        return Observable.zip(scheduledActions, fsm, BiFunction { event: ScheduledActions, fsmLis: FsmList ->
            combine(event, fsmLis)
        })
    }

    private fun combine(scheduledActions: ScheduledActions, fsmList: FsmList): ScheduledActionsWithState {
        return ScheduledActionsWithState(scheduledActions.actions, fsmList.fsm)
    }
}
