package com.fiwio.iot.demeter.domain.repository

import com.fiwio.iot.demeter.domain.model.*
import io.reactivex.Observable
import io.reactivex.Single

interface DemeterRepository {
    fun getDemeterImage(): Observable<Demeter>
    fun getSchedules(): Observable<ScheduledActions>
    fun switchActuator(actuator: Actuator)
    fun getFsm(): Observable<FsmList>
    fun refresh()
}