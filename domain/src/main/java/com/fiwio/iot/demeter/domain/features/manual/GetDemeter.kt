package com.fiwio.iot.demeter.domain.features.manual

import com.fiwio.iot.demeter.domain.executor.PostExecutionThread
import com.fiwio.iot.demeter.domain.executor.ThreadExecutor
import io.reactivex.Single
import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.interactor.SingleUseCase
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import javax.inject.Inject

/**
 * Use case used for retreiving a [Demeter] instances from the [DemeterRepository]
 */
open class GetDemeter @Inject constructor(val demeterRepository: DemeterRepository,
                                          threadExecutor: ThreadExecutor,
                                          postExecutionThread: PostExecutionThread) :
        SingleUseCase<Demeter, Void?>(threadExecutor, postExecutionThread) {
     public override fun buildUseCaseObservable(params: Void?): Single<Demeter> {
        return demeterRepository.getDemeterImage()
    }
}