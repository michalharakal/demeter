package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.model.ActuatorEntity
import com.fiwio.iot.demeter.data.model.DemeterEntity
import com.fiwio.iot.demeter.data.model.FsmListEnitities
import com.fiwio.iot.demeter.data.model.ScheduledActionsEntity

interface DemeterDataStore {
    fun getDemeterImage(): DemeterEntity
    fun saveDemeterImage(demeter: DemeterEntity)

    fun switchActuator(actuatorEntity: ActuatorEntity): DemeterEntity

    fun getScheduledActions(): ScheduledActionsEntity
    fun saveScheduledActions(scheduledActions: ScheduledActionsEntity)

    fun getFsm(): FsmListEnitities
    fun saveFsmEntities(fsmListEnitities: FsmListEnitities)
}