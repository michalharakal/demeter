package com.fiwio.iot.demeter.presentation.feature.message

import com.fiwio.iot.demeter.domain.features.messages.GetMessages
import com.fiwio.iot.demeter.domain.model.Messages
import com.fiwio.iot.demeter.domain.model.ScheduledActionsWithState
import com.fiwio.iot.demeter.presentation.feature.schedule.ScheduleContract
import com.fiwio.iot.demeter.presentation.mapper.MessagesMapper
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MessagesPresenter @Inject constructor(val getMessages: GetMessages, val messagesMapper: MessagesMapper) :
        MessagesContract.Presenter {
    var view: MessagesContract.View? = null

    override fun attachView(view: MessagesContract.View) {
        this.view = view
        subscribeForChanges()
    }

    override fun detachView() {
        getMessages.dispose()
    }

    override fun destroy() {
    }

    private fun subscribeForChanges() {

        getMessages.execute(MessagesSubscriber())
    }

    inner class MessagesSubscriber : DisposableObserver<Messages>() {
        override fun onComplete() {

        }

        override fun onNext(t: Messages) {
            view?.setData(messagesMapper.mapToView(t))
        }

        override fun onError(e: Throwable) {
            view?.showError(e, false)
        }

    }

}
