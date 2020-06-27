package com.fiwio.iot.demeter.domain.features.manual

import com.fiwio.iot.demeter.domain.executor.PostExecutionThread
import com.fiwio.iot.demeter.domain.executor.ThreadExecutor
import com.fiwio.iot.demeter.domain.interactor.ObservableUseCase
import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetDemeter @Inject constructor(val demeterRepository: DemeterRepository,
                                     threadExecutor: ThreadExecutor,
                                     postExecutionThread: PostExecutionThread) :
        ObservableUseCase<Demeter, Void?>(threadExecutor, postExecutionThread) {
    public override fun buildUseCaseObservable(params: Void?): Observable<Demeter> {
        return demeterRepository.getDemeterImage()
    }
}