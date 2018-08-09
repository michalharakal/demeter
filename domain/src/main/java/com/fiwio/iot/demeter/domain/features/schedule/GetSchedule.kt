package com.fiwio.iot.demeter.domain.features.schedule

import com.fiwio.iot.demeter.domain.executor.PostExecutionThread
import com.fiwio.iot.demeter.domain.executor.ThreadExecutor
import com.fiwio.iot.demeter.domain.interactor.SingleUseCase
import com.fiwio.iot.demeter.domain.model.ScheduledActions
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import io.reactivex.Single
import javax.inject.Inject

open class GetSchedule @Inject constructor(val demeterRepository: DemeterRepository,
                                           threadExecutor: ThreadExecutor,
                                           postExecutionThread: PostExecutionThread) :
        SingleUseCase<ScheduledActions, Void?>(threadExecutor, postExecutionThread) {
    public override fun buildUseCaseObservable(params: Void?): Single<ScheduledActions> {
        return demeterRepository.getSchedules()
    }
}