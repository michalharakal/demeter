package com.fiwio.iot.demeter.presentation.feature.manual

import com.fiwio.iot.demeter.domain.features.manual.GetDemeter
import com.fiwio.iot.demeter.domain.features.manual.SwitchActuator
import com.fiwio.iot.demeter.domain.model.Actuator
import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.presentation.model.ActuatorState
import com.fiwio.iot.demeter.presentation.model.ActuatorView
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import mu.KotlinLogging
import org.buffer.android.boilerplate.presentation.mapper.ActuatorViewMapper
import javax.inject.Inject

private val logger = KotlinLogging.logger {}

class ActuatorsListPresenter @Inject constructor(
        val getDemeter: GetDemeter,
        val setActuator: SwitchActuator,
        val actuatorViewMapper: ActuatorViewMapper) :
        ActuatorsListContract.Presenter {

    var view: ActuatorsListContract.View? = null

    override fun attachView(view: ActuatorsListContract.View) {
        this.view = view
        getDemeter.execute(GetDemeterSubscriber())
    }

    inner class GetDemeterSubscriber : DisposableObserver<Demeter>() {
        override fun onComplete() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onNext(t: Demeter) {
            handleDemeterEventSuccess(t)
        }

        override fun onError(exception: Throwable) {
            view?.showError(exception, false)
        }

    }

    override fun detachView() {
        getDemeter.dispose()
    }

    override fun destroy() {
    }

    override fun switchRelay(actuator: ActuatorView) {
        setActuator.execute(DemeterSubscriber(), Actuator(actuator.name, actuator.state != ActuatorState.ON))
    }

    private fun handleDemeterEventSuccess(t: Demeter) {
        view?.setData(t.actuators
                .map { it ->
                    actuatorViewMapper.mapToView(it)
                })
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

