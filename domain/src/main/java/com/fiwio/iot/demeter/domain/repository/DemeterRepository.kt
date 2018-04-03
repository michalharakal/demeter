package com.fiwio.iot.demeter.domain.repository

import com.fiwio.iot.demeter.domain.model.Actuator
import com.fiwio.iot.demeter.domain.model.Change
import com.fiwio.iot.demeter.domain.model.Demeter
import io.reactivex.Observable
import io.reactivex.Single

interface DemeterRepository {
    fun getDemeterImage(): Single<Demeter>
    fun getEventChanges(): Observable<Change>
    fun switchActuator(actuator: Actuator): Single<Demeter>
}