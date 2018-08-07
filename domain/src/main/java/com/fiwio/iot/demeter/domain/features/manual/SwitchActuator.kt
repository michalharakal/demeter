package com.fiwio.iot.demeter.domain.features.manual

import com.fiwio.iot.demeter.domain.executor.PostExecutionThread
import com.fiwio.iot.demeter.domain.executor.ThreadExecutor
import com.fiwio.iot.demeter.domain.interactor.SingleUseCase
import com.fiwio.iot.demeter.domain.model.Actuator
import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import io.reactivex.Single
import javax.inject.Inject

open class SwitchActuator @Inject constructor(val demeterRepository: DemeterRepository,
                                              threadExecutor: ThreadExecutor,
                                              postExecutionThread: PostExecutionThread) :
        SingleUseCase<Demeter, Actuator>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Actuator?): Single<Demeter> {
        return demeterRepository.switchActuator(params as Actuator)
    }
}