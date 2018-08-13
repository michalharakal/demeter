package com.fiwio.iot.demeter.domain.features.messages

import com.fiwio.iot.demeter.domain.executor.PostExecutionThread
import com.fiwio.iot.demeter.domain.executor.ThreadExecutor
import com.fiwio.iot.demeter.domain.interactor.ObservableUseCase
import com.fiwio.iot.demeter.domain.model.Messages
import com.fiwio.iot.demeter.domain.repository.MessagesRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetMessages @Inject constructor(val messagesRepository: MessagesRepository,
                                      threadExecutor: ThreadExecutor,
                                      postExecutionThread: PostExecutionThread) :
        ObservableUseCase<Messages, Void?>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Void?): Observable<Messages> {
        return messagesRepository.getMessages()
    }
}
