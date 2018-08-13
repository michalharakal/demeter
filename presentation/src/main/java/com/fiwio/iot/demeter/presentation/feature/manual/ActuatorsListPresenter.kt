package com.fiwio.iot.demeter.presentation.feature.manual

import com.fiwio.iot.demeter.domain.features.manual.GetDemeter
import com.fiwio.iot.demeter.domain.features.manual.SwitchActuator
import com.fiwio.iot.demeter.domain.model.Actuator
import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import com.fiwio.iot.demeter.presentation.model.ActuatorState
import com.fiwio.iot.demeter.presentation.model.ActuatorView
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import mu.KotlinLogging
import org.buffer.android.boilerplate.presentation.mapper.ActuatorViewMapper
import javax.inject.Inject

private val logger = KotlinLogging.logger {}

class ActuatorsListPresenter @Inject constructor(
        val demeterRepository: DemeterRepository,
        val getDemeter: GetDemeter,
        val setActuator: SwitchActuator,
        val actuatorViewMapper: ActuatorViewMapper) :
        ActuatorsListContract.Presenter {

    var view: ActuatorsListContract.View? = null

    override fun attachView(view: ActuatorsListContract.View) {
        this.view = view
        subscribeForChanges()
    }

    override fun detachView() {
        getDemeter.dispose()
        events.dispose()
    }

    override fun destroy() {
    }

    override fun switchRelay(actuator: ActuatorView) {
        setActuator.execute(DemeterSubscriber(), Actuator(actuator.name, actuator.state != ActuatorState.ON))
    }

    private lateinit var events: Disposable

    private fun subscribeForChanges() {
        events = demeterRepository.getEventChanges()
                .subscribeOn(Schedulers.newThread())
                .subscribe { _ ->
                    getDemeter.execute(DemeterSubscriber())
                }
    }

    private fun handleDemeterEventSuccess(t: Demeter) {
        view?.setData(t.actuators
                //.filter { actuator -> actuator.name.equals("BCM23")  }
                .map { it ->
                    actuatorViewMapper.mapToView(it)
                }
                /*.filter { it ->
                    //!it.name.equals("BCM27")
                }*/)
    }

    inner class DemeterSubscriber : DisposableSingleObserver<Demeter>() {

        override fun onSuccess(t: Demeter) {
            logger.debug { "receives a new demeter image" }
            handleDemeterEventSuccess(t)
        }

        override fun onError(exception: Throwable) {
            view?.showError(exception, false)
        }
    }
}

