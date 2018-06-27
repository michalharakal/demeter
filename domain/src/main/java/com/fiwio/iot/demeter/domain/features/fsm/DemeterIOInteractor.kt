package com.fiwio.iot.demeter.domain.features.fsm

import au.com.ds.ef.StatefulContext
import com.fiwio.iot.demeter.domain.features.manual.SwitchActuator
import com.fiwio.iot.demeter.domain.features.tracking.EventTracker
import com.fiwio.iot.demeter.domain.model.Actuator
import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.model.InputValue
import com.fiwio.iot.demeter.domain.model.Sensor
import io.reactivex.observers.DisposableSingleObserver

class DemeterIOInteractor(val barrel_pump: Actuator, val barrel_input: Sensor, val barrel_valve: Actuator,
                          val switchActuator: SwitchActuator, val tracker: EventTracker) : IOInteractor {

    override fun swimmerIsInactive(): Boolean {
        return (barrel_input.value === InputValue.ON);
    }

    override fun openBarrel(ctx: StatefulContext) {
        switchActuator.execute(object : DisposableSingleObserver<Demeter>() {
            override fun onSuccess(demeter: Demeter) {
                tracker.track("barrel was opened:" + ctx.id)
            }

            override fun onError(e: Throwable) {
                tracker.track("error by barrel opening;" + ctx.id)
            }
        }, Actuator(barrel_valve.name, true))
    }

    override fun barrelPumpOn(ctx: StatefulContext) {
        switchActuator.execute(object : DisposableSingleObserver<Demeter>() {
            override fun onSuccess(demeter: Demeter) {
                tracker.track("pump was started:" + ctx.id)
            }

            override fun onError(e: Throwable) {
                tracker.track("error by pump start:" + ctx.id)
            }
        }, Actuator(barrel_pump.name, true))
    }

    // TODO create batch command for settings multiple actuator at once
    override fun closeAllVentils(ctx: StatefulContext) {
        switchActuator.execute(object : DisposableSingleObserver<Demeter>() {
            override fun onSuccess(demeter: Demeter) {
                tracker.track("barrel was closed:" + ctx.id)
            }

            override fun onError(e: Throwable) {
                tracker.track("error by brrel closing:" + ctx.id)

            }
        }, Actuator(barrel_valve.name, false))

        switchActuator.execute(object : DisposableSingleObserver<Demeter>() {
            override fun onSuccess(demeter: Demeter) {
                tracker.track("pump was stoped:" + ctx.id)
            }

            override fun onError(e: Throwable) {
                tracker.track("error by stoping the pump:" + ctx.id)

            }
        }, Actuator(barrel_pump.name, false))

    }
}