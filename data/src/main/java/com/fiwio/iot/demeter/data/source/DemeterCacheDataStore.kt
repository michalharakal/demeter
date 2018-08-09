package com.fiwio.iot.demeter.data.source

import com.fiwio.iot.demeter.data.model.ActuatorEntity
import com.fiwio.iot.demeter.data.model.DemeterEntity
import com.fiwio.iot.demeter.data.model.ScheduledActionsEntity
import com.fiwio.iot.demeter.data.repository.DemeterCache
import com.fiwio.iot.demeter.data.repository.DemeterDataStore
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of the [DemeterDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class DemeterCacheDataStore @Inject constructor(private val demeterCache: DemeterCache) :
        DemeterDataStore {
    override fun getScheduledActions(): Single<ScheduledActionsEntity> {
        return demeterCache.getScheduledActions()
    }

    override fun switchActuator(actuatorEntity: ActuatorEntity): Single<DemeterEntity> {
        throw UnsupportedOperationException()
    }

    override fun saveDemeterImage(demeter: DemeterEntity): Completable {
        return demeterCache.saveDemeterImage(demeter)
    }

    override fun getDemeterImage(): Single<DemeterEntity> {
        return demeterCache.getDemeterImage()
    }
}
