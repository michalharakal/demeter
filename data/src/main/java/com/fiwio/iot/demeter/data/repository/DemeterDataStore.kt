package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.model.ActuatorEntity
import com.fiwio.iot.demeter.data.model.DemeterEntity
import com.fiwio.iot.demeter.data.model.FsmListEnitities
import com.fiwio.iot.demeter.data.model.ScheduledActionsEntity
import com.fiwio.iot.demeter.domain.model.FsmList
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface DemeterDataStore {
    fun getDemeterImage(): Single<DemeterEntity>
    fun getScheduledActions(): Observable<ScheduledActionsEntity>
    fun switchActuator(actuatorEntity: ActuatorEntity): Single<DemeterEntity>
    fun saveDemeterImage(demeter: DemeterEntity): Completable
    fun getFsm(): Observable<FsmListEnitities>
}