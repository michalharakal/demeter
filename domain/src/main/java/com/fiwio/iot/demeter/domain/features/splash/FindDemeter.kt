package com.fiwio.iot.demeter.domain.features.splash

import com.fiwio.iot.demeter.domain.executor.PostExecutionThread
import com.fiwio.iot.demeter.domain.executor.ThreadExecutor
import com.fiwio.iot.demeter.domain.interactor.ObservableUseCase
import com.fiwio.iot.demeter.domain.interactor.SingleUseCase
import com.fiwio.iot.demeter.domain.model.DemeterSearchDnsInfo
import com.fiwio.iot.demeter.domain.repository.DemeterFinder
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject


/**
 * Use case used for retreiving a single [Event] instances from the [ConferenceRepository] with id
 */
open class FindDemeter @Inject constructor(val demeterFinder: DemeterFinder,
                                           threadExecutor: ThreadExecutor,
                                           postExecutionThread: PostExecutionThread) :
        ObservableUseCase<DemeterSearchDnsInfo, Any?>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Any?): Observable<DemeterSearchDnsInfo> {
        return demeterFinder.findDemeterUrl()
    }
}