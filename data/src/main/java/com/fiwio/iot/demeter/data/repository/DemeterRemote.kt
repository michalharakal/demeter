package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.model.ActuatorEntity
import com.fiwio.iot.demeter.data.model.DemeterEntity
import com.fiwio.iot.demeter.data.model.FsmListEnitities
import com.fiwio.iot.demeter.data.model.ScheduledActionsEntity
import io.reactivex.Observable
import io.reactivex.Single

interface DemeterRemote {
    fun getDemeterImage(): Single<DemeterEntity>
    fun switchActuator(actuatorEntity: ActuatorEntity): Single<DemeterEntity>
    fun getScheduledActions(): Observable<ScheduledActionsEntity>
    fun getFsm(): Observable<FsmListEnitities>
}