package com.fiwio.iot.demeter.domain.repository

import com.fiwio.iot.demeter.domain.model.Actuator
import com.fiwio.iot.demeter.domain.model.Change
import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.model.ScheduledActions
import io.reactivex.Observable
import io.reactivex.Single

interface DemeterRepository {
    fun getDemeterImage(): Single<Demeter>
    fun getSchedules(): Single<ScheduledActions>
    fun getEventChanges(): Observable<Change>
    fun switchActuator(actuator: Actuator): Single<Demeter>
    fun refresh()
}