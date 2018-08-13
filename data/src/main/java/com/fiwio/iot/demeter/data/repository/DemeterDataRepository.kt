package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.mapper.ActuatorMapper
import com.fiwio.iot.demeter.data.mapper.FsmMapper
import com.fiwio.iot.demeter.data.mapper.ScheduledActionMapper
import com.fiwio.iot.demeter.data.model.DemeterEntity
import com.fiwio.iot.demeter.data.source.DemeterDataSourceFactory
import com.fiwio.iot.demeter.domain.model.*
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.dukecon.data.mapper.DemeterMapper
import javax.inject.Inject

class DemeterDataRepository @Inject constructor(private val demeterDataSourceFactory: DemeterDataSourceFactory,
                                                private val demeterMapper: DemeterMapper,
                                                private val schedulesMapper: ScheduledActionMapper,
                                                private val demetrCache: DemeterCache,
                                                private val fsmMapper: FsmMapper,
                                                val actuatorMapper: ActuatorMapper)
    : DemeterRepository {
    override fun getFsm(): Observable<FsmList> {
        return demeterDataSourceFactory.retrieveDataStore()
                .getFsm()
                .map {
                    fsmMapper.mapFromEntity(it)
                }
    }

    override fun getSchedules(): Observable<ScheduledActions> {
        return demeterDataSourceFactory.retrieveDataStore()
                .getScheduledActions()
                .map {
                    schedulesMapper.mapFromEntity(it)
                }
    }

    override fun refresh() {
        relay.accept(Change(DataChange.ACTUATOR))
    }

    override fun switchActuator(actuator: Actuator): Single<Demeter> {
        return demeterDataSourceFactory.retrieveRemoteDataStore()
                .switchActuator(actuatorMapper.mapToEntity(actuator))
                .doAfterSuccess {
                    demetrCache.invalidate()
                    //  saveDemeterEntities(it).toSingle { it }
                    relay.accept(Change(DataChange.ACTUATOR))
                }
                .map { demeterMapper.mapFromEntity(it) }
    }

    override fun getDemeterImage(): Single<Demeter> {
        return demeterDataSourceFactory.retrieveDataStore()
                .getDemeterImage()
                .map {
                    demeterMapper.mapFromEntity(it)
                }

    }

    private var relay: PublishRelay<Change> = PublishRelay.create<Change>()

    override fun getEventChanges(): Observable<Change> {
        return relay
    }


    private fun saveDemeterEntities(demeter: DemeterEntity): Completable {
        return demeterDataSourceFactory.retrieveCacheDataStore().saveDemeterImage(demeter)
    }
}