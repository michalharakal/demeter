package com.fiwio.iot.demeter.presentation.feature.schedule

import com.fiwio.iot.demeter.domain.features.schedule.GetSchedule
import com.fiwio.iot.demeter.domain.model.ScheduledActions
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import com.fiwio.iot.demeter.presentation.mapper.ScheduledActionMapper
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import mu.KotlinLogging
import javax.inject.Inject

private val logger = KotlinLogging.logger {}

class SchedulesPresenter @Inject constructor(
        val demeterRepository: DemeterRepository,
        val getSchedule: GetSchedule,
        val scheduledActionMapper: ScheduledActionMapper) :
        ScheduleContract.Presenter {

    var view: ScheduleContract.View? = null

    override fun attachView(view: ScheduleContract.View) {
        this.view = view
        subscribeForChanges()
    }

    override fun detachView() {
        getSchedule.dispose()
    }

    override fun destroy() {
    }


    private fun subscribeForChanges() {
        getSchedule.execute(SechedulesSubscriber())
    }

    inner class SechedulesSubscriber : DisposableSingleObserver<ScheduledActions>() {
        override fun onSuccess(t: ScheduledActions) {
            view?.setData(scheduledActionMapper.mapToView(t))
        }

        override fun onError(exception: Throwable) {
            view?.showError(exception, false)
        }
    }
}

