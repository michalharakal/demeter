package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.mapper.ActuatorMapper
import com.fiwio.iot.demeter.data.mapper.FsmMapper
import com.fiwio.iot.demeter.data.mapper.ScheduledActionMapper
import com.fiwio.iot.demeter.data.model.DemeterEntity
import com.fiwio.iot.demeter.data.model.FsmListEnitities
import com.fiwio.iot.demeter.data.source.DemeterDataSourceFactory
import com.fiwio.iot.demeter.domain.model.Actuator
import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.model.FsmList
import com.fiwio.iot.demeter.domain.model.ScheduledActions
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.dukecon.data.mapper.DemeterMapper
import javax.inject.Inject

class DemeterDataRepository @Inject constructor(private val demeterDataSourceFactory: DemeterDataSourceFactory,
                                                private val demeterMapper: DemeterMapper,
                                                private val schedulesMapper: ScheduledActionMapper,
                                                private val fsmMapper: FsmMapper,
                                                private val actuatorMapper: ActuatorMapper)
    : DemeterRepository {

    object Obervables {
        val demeterObservable: BehaviorSubject<Demeter> = BehaviorSubject.create<Demeter>()
        val scheduledActionsObservable: BehaviorSubject<ScheduledActions> = BehaviorSubject.create<ScheduledActions>()
        val fsmObservable: BehaviorSubject<FsmList> = BehaviorSubject.create<FsmList>()
    }

    override fun refresh() {
        // refresh demeter image
        with(demeterDataSourceFactory.retrieveRemoteDataStore().getDemeterImage()) {
            saveDemeterEntities(this)
        }
        // refresh fsm image
        with(demeterDataSourceFactory.retrieveDataStore().getFsm()) {
            saveFsmEntities(this)
        }

        // refresh scheduled actions
        with(demeterDataSourceFactory.retrieveDataStore().getScheduledActions()) {
            Obervables.scheduledActionsObservable.onNext(
                    schedulesMapper.mapFromEntity(this)
            )
        }
    }

    override fun getDemeterImage(): Observable<Demeter> {
        with(demeterDataSourceFactory.retrieveCacheDataStore().getDemeterImage()) {
            Obervables.demeterObservable.onNext(demeterMapper.mapFromEntity(this))
        }
        return Obervables.demeterObservable
    }

    override fun getFsm(): Observable<FsmList> {
        with(demeterDataSourceFactory.retrieveCacheDataStore().getFsm()) {
            Obervables.fsmObservable.onNext(fsmMapper.mapFromEntity(this))
        }
        return Obervables.fsmObservable
    }

    override fun getSchedules(): Observable<ScheduledActions> {
        Obervables.scheduledActionsObservable.onNext(
                with(demeterDataSourceFactory.retrieveCacheDataStore().getScheduledActions()) {
                    schedulesMapper.mapFromEntity(this)
                }
        )
        return Obervables.scheduledActionsObservable
    }

    override fun switchActuator(actuator: Actuator) {
        with(demeterDataSourceFactory.retrieveCacheDataStore().switchActuator(actuatorMapper.mapToEntity(actuator))) {
            saveDemeterEntities(this)
            Obervables.demeterObservable.onNext(
                    demeterMapper.mapFromEntity(this)
            )
        }
    }


    private fun saveDemeterEntities(demeter: DemeterEntity) {
        demeterDataSourceFactory.retrieveCacheDataStore().saveDemeterImage(demeter)
        Obervables.demeterObservable.onNext(
                demeterMapper.mapFromEntity(demeter)
        )
    }

    private fun saveFsmEntities(fsmListEntities: FsmListEnitities) {
        demeterDataSourceFactory.retrieveCacheDataStore().saveFsmEntities(fsmListEntities)
        Obervables.fsmObservable.onNext(
                fsmMapper.mapFromEntity(fsmListEntities)
        )

    }

}