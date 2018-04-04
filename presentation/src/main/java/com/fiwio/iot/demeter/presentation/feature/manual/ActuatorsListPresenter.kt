package com.fiwio.iot.demeter.presentation.feature.manual

import com.fiwio.iot.demeter.domain.features.manual.GetDemeter
import com.fiwio.iot.demeter.domain.features.manual.SetActuator
import com.fiwio.iot.demeter.domain.model.Actuator
import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import com.fiwio.iot.demeter.presentation.model.ActuatorState
import com.fiwio.iot.demeter.presentation.model.ActuatorView
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.buffer.android.boilerplate.presentation.mapper.ActuatorViewMapper
import javax.inject.Inject

class ActuatorsListPresenter @Inject constructor(
        val demeterRepository: DemeterRepository,
        val getDemeter: GetDemeter,
        val setActuator: SetActuator,
        val actuatorViewMapper: ActuatorViewMapper) :
        ActuatorsListContract.Presenter {

    var view: ActuatorsListContract.View? = null

    override fun attachView(view: ActuatorsListContract.View) {
        this.view = view
        subscribeForChanges()
    }

    override fun detachView() {
        getDemeter.dispose()
    }

    override fun destroy() {
    }

    override fun switchRelay(actuator: ActuatorView) {
        setActuator.execute(DemeterSubscriber(), Actuator(actuator.name, actuator.state != ActuatorState.ON))
    }

    private fun subscribeForChanges() {
        demeterRepository.getDemeterImage()
                .subscribeOn(Schedulers.newThread())
                .subscribe { _ ->
                    getDemeter.execute(DemeterSubscriber())
                }
    }

    private fun handleDemeterEventSuccess(t: Demeter) {
        view?.setData(t.actuators.map { it ->
            actuatorViewMapper.mapToView(it)
        })
    }

    inner class DemeterSubscriber : DisposableSingleObserver<Demeter>() {

        override fun onSuccess(t: Demeter) {
            handleDemeterEventSuccess(t)
        }

        override fun onError(exception: Throwable) {
            view?.showError(exception, false)
        }
    }
}

