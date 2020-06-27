package com.fiwio.iot.demeter.domain.features.messages

import com.fiwio.iot.demeter.domain.executor.PostExecutionThread
import com.fiwio.iot.demeter.domain.executor.ThreadExecutor
import com.fiwio.iot.demeter.domain.interactor.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

open class ShowNotification @Inject constructor(val notificationGateway: NotificationGateway,
                                            threadExecutor: ThreadExecutor,
                                            postExecutionThread: PostExecutionThread) :
        SingleUseCase<Unit, String>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: String?): Single<Unit> {
        return Single.just(
                notificationGateway.showNotification(params!!)
        )
    }
}

