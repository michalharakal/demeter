package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.model.ActuatorEntity
import com.fiwio.iot.demeter.data.model.DemeterEntity
import io.reactivex.Single

interface DemeterRemote {
    fun getDemeterImage(): Single<DemeterEntity>
    fun switchActuator(actuatorEntity: ActuatorEntity): Single<DemeterEntity>
}