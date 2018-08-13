package com.fiwio.iot.demeter.data.source

import com.fiwio.iot.demeter.data.model.ActuatorEntity
import com.fiwio.iot.demeter.data.model.DemeterEntity
import com.fiwio.iot.demeter.data.model.FsmListEnitities
import com.fiwio.iot.demeter.data.model.ScheduledActionsEntity
import com.fiwio.iot.demeter.data.repository.DemeterDataStore
import com.fiwio.iot.demeter.data.repository.DemeterRemote
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of the [DemeterDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class DemeterRemoteDataStore @Inject constructor(private val eventRemote: DemeterRemote) :
        DemeterDataStore {
    override fun getFsm(): Observable<FsmListEnitities> {
        return eventRemote.getFsm()
    }

    override fun getScheduledActions(): Observable<ScheduledActionsEntity> {
        return eventRemote.getScheduledActions()
    }

    override fun switchActuator(actuatorEntity: ActuatorEntity): Single<DemeterEntity> {
        return eventRemote.switchActuator(actuatorEntity)
    }

    override fun saveDemeterImage(demeter: DemeterEntity): Completable {
        throw UnsupportedOperationException()
    }

    override fun getDemeterImage(): Single<DemeterEntity> {
        return eventRemote.getDemeterImage()
    }
}
