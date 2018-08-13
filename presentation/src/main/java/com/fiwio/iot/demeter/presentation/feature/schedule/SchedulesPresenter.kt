package com.fiwio.iot.demeter.presentation.feature.schedule

import com.fiwio.iot.demeter.domain.features.schedule.GetScheduleWithFsm
import com.fiwio.iot.demeter.domain.model.ScheduledActionsWithState
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import com.fiwio.iot.demeter.presentation.mapper.ScheduledActionMapper
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import mu.KotlinLogging
import javax.inject.Inject

private val logger = KotlinLogging.logger {}

class SchedulesPresenter @Inject constructor(
        val getScheduleWithFsm: GetScheduleWithFsm,
        val scheduledActionMapper: ScheduledActionMapper,
        val demeterRepository: DemeterRepository) :
        ScheduleContract.Presenter {

    var view: ScheduleContract.View? = null

    override fun attachView(view: ScheduleContract.View) {
        this.view = view
        subscribeForChanges()
    }

    override fun detachView() {
        getScheduleWithFsm.dispose()
    }

    override fun destroy() {
    }


    private lateinit var events: Disposable

    private fun subscribeForChanges() {
        events = demeterRepository.getEventChanges()
                .subscribeOn(Schedulers.newThread())
                .subscribe { _ ->
                    getScheduleWithFsm.execute(SchedulesSubscriber())
                }
    }


    inner class SchedulesSubscriber : DisposableObserver<ScheduledActionsWithState>() {
        override fun onComplete() {
        }

        override fun onNext(t: ScheduledActionsWithState) {
            view?.setData(scheduledActionMapper.mapToView(t))
        }

        override fun onError(e: Throwable) {
            view?.showError(e, false)
        }

    }

}

