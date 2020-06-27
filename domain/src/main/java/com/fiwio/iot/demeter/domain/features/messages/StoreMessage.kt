package com.fiwio.iot.demeter.domain.features.messages

import com.fiwio.iot.demeter.domain.executor.PostExecutionThread
import com.fiwio.iot.demeter.domain.executor.ThreadExecutor
import com.fiwio.iot.demeter.domain.interactor.SingleUseCase
import com.fiwio.iot.demeter.domain.model.Message
import com.fiwio.iot.demeter.domain.repository.MessagesRepository
import io.reactivex.Single
import javax.inject.Inject

open class StoreMessage @Inject constructor(val messagesRepository: MessagesRepository,
                                            threadExecutor: ThreadExecutor,
                                            postExecutionThread: PostExecutionThread) :
        SingleUseCase<Unit, Message>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Message?): Single<Unit> {
        return Single.just(
                messagesRepository.add(params!!)
        )
    }
}

