package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.model.ActuatorEntity
import com.fiwio.iot.demeter.data.model.DemeterEntity
import com.fiwio.iot.demeter.data.model.ScheduledActionsEntity
import com.fiwio.iot.demeter.domain.model.Demeter
import io.reactivex.Completable
import io.reactivex.Single

interface DemeterDataStore {
    fun getDemeterImage(): Single<DemeterEntity>
    fun getScheduledActions(): Single<ScheduledActionsEntity>
    fun switchActuator(actuatorEntity: ActuatorEntity): Single<DemeterEntity>
    fun saveDemeterImage(demeter: DemeterEntity): Completable
}