package com.fiwio.iot.demeter.data.source

import com.fiwio.iot.demeter.data.model.ActuatorEntity
import com.fiwio.iot.demeter.data.model.DemeterEntity
import com.fiwio.iot.demeter.data.model.FsmListEnitities
import com.fiwio.iot.demeter.data.model.ScheduledActionsEntity
import com.fiwio.iot.demeter.data.repository.DemeterDataStore
import com.fiwio.iot.demeter.data.repository.DemeterRemote
import javax.inject.Inject

open class DemeterRemoteDataStore @Inject constructor(private val eventRemote: DemeterRemote) :
        DemeterDataStore {
    override fun saveScheduledActions(scheduledActions: ScheduledActionsEntity) {
        throw UnsupportedOperationException()
    }

    override fun saveFsmEntities(fsmListEnitities: FsmListEnitities) {
        throw UnsupportedOperationException()
    }

    override fun getFsm(): FsmListEnitities {
        return eventRemote.getFsm()
    }

    override fun getScheduledActions(): ScheduledActionsEntity {
        return eventRemote.getScheduledActions()
    }

    override fun switchActuator(actuatorEntity: ActuatorEntity): DemeterEntity {
        return eventRemote.switchActuator(actuatorEntity)
    }

    override fun saveDemeterImage(demeter: DemeterEntity) {
        throw UnsupportedOperationException()
    }

    override fun getDemeterImage(): DemeterEntity {
        return eventRemote.getDemeterImage()
    }
}
