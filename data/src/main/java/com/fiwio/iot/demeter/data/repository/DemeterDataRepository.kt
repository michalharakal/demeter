package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.mapper.ActuatorMapper
import com.fiwio.iot.demeter.data.model.DemeterEntity
import com.fiwio.iot.demeter.data.source.DemeterDataSourceFactory
import com.fiwio.iot.demeter.domain.model.Actuator
import com.fiwio.iot.demeter.domain.model.Change
import com.fiwio.iot.demeter.domain.model.DataChange
import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.dukecon.data.mapper.DemeterMapper
import javax.inject.Inject

class DemeterDataRepository @Inject constructor(private val demeterDataSourceFactory: DemeterDataSourceFactory,
                                                private val demeterMapper: DemeterMapper,
                                                val actuatorMapper: ActuatorMapper)
    : DemeterRepository {
    override fun switchActuator(actuator: Actuator): Single<Demeter> {
        return demeterDataSourceFactory.retrieveRemoteDataStore()
                .switchActuator(actuatorMapper.mapToEntity(actuator))
                .doAfterSuccess {
                    saveDemeterEntities(it).toSingle() { it }
                    relay.accept(Change(DataChange.ACTUATOR))
                }
                .map { demeterMapper.mapFromEntity(it) }
    }

    override fun getDemeterImage(): Single<Demeter> {
        return demeterDataSourceFactory.retrieveDataStore()
                .getDemeterImage().map { demeterMapper.mapFromEntity(it) }
    }

    private var relay: PublishRelay<Change> = PublishRelay.create<Change>()

    override fun getEventChanges(): Observable<Change> {
        return relay
    }


    private fun saveDemeterEntities(demeter: DemeterEntity): Completable {
        return demeterDataSourceFactory.retrieveCacheDataStore().saveDemeterImage(demeter)
    }
}