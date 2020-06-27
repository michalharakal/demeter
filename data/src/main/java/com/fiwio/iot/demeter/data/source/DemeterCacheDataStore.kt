package com.fiwio.iot.demeter.data.source

import com.fiwio.iot.demeter.data.model.ActuatorEntity
import com.fiwio.iot.demeter.data.model.DemeterEntity
import com.fiwio.iot.demeter.data.model.FsmListEnitities
import com.fiwio.iot.demeter.data.model.ScheduledActionsEntity
import com.fiwio.iot.demeter.data.repository.DemeterCache
import com.fiwio.iot.demeter.data.repository.DemeterDataStore
import javax.inject.Inject

/**
 * Implementation of the [DemeterDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class DemeterCacheDataStore @Inject constructor(private val demeterCache: DemeterCache) :
        DemeterDataStore {
    override fun saveScheduledActions(scheduledActions: ScheduledActionsEntity) {
        demeterCache.saveScheduledActions(scheduledActions)
    }

    override fun saveFsmEntities(fsmListEnitities: FsmListEnitities) {
        demeterCache.saveFsmEntities(fsmListEnitities)
    }

    override fun getFsm(): FsmListEnitities {
        return demeterCache.getFsm()
    }

    override fun getScheduledActions(): ScheduledActionsEntity {
        return demeterCache.getScheduledActionsEntities()
    }

    override fun switchActuator(actuatorEntity: ActuatorEntity): DemeterEntity {
        throw UnsupportedOperationException()
    }

    override fun saveDemeterImage(demeter: DemeterEntity) {
        return demeterCache.saveDemeterImage(demeter)
    }

    override fun getDemeterImage(): DemeterEntity {
        return demeterCache.getDemeterImage()
    }
}
