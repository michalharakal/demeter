package com.fiwio.iot.demeter.domain.repository

import com.fiwio.iot.demeter.domain.model.*
import io.reactivex.Observable
import io.reactivex.Single

interface DemeterRepository {
    fun getDemeterImage(): Single<Demeter>
    fun getSchedules(): Observable<ScheduledActions>
    fun getEventChanges(): Observable<Change>
    fun switchActuator(actuator: Actuator): Single<Demeter>
    fun refresh()
    fun getFsm(): Observable<FsmList>
}