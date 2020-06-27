package com.fiwio.iot.demeter.remote.firebase.remote.source

import com.fiwio.iot.demeter.data.model.*
import com.fiwio.iot.demeter.data.repository.DemeterRemote
import com.fiwio.iot.demeter.domain.connectivity.ConnectionSession
import com.fiwio.iot.demeter.remote.firebase.remote.firebase.mapper.DemeterEntityMapper
import com.fiwio.iot.demeter.remote.firebase.remote.firebase.mapper.FsmEntityMapper
import com.fiwio.iot.demeter.remote.firebase.remote.firebase.mapper.SchedulesEntityMapper
import com.fiwo.iot.demeter.api.DefaultApi
import com.fiwo.iot.demeter.api.model.DigitalOutputs
import com.fiwo.iot.demeter.api.model.Relay
import javax.inject.Inject


/**
 * Remote implementation for retrieving Event instances. This class implements the
 * [EventRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class DemeterRemoteImpl @Inject constructor(private val session: ConnectionSession,
                                            private val demeterApi: DefaultApi,
                                            private val entityMapper: DemeterEntityMapper,
                                            private val schedulesEntityMapper: SchedulesEntityMapper,
                                            private val fsmEntityMapper: FsmEntityMapper) :
        DemeterRemote {

    override fun getScheduledActions(): ScheduledActionsEntity {
        val call = demeterApi.getSchedule(session.getCmlId())
        val response = call.execute()
        if (response.isSuccessful) {
            if (response.body() != null) {
                val eventsList = response.body()
                if (eventsList != null) {
                    return schedulesEntityMapper.mapFromRemote(eventsList)
                }
            }
        }
        return ScheduledActionsEntity(emptyList())
    }

    override fun getFsm(): FsmListEnitities {
        val call = demeterApi.getFsm(session.getCmlId())
        val response = call.execute()
        if (response.isSuccessful) {
            if (response.body() != null) {
                val eventsList = response.body()
                if (eventsList != null) {
                    return fsmEntityMapper.mapFromRemote(eventsList)
                }
            }
        }
        return FsmListEnitities(emptyList())
    }

    override fun switchActuator(actuatorEntity: ActuatorEntity): DemeterEntity {
        // create list with one actuator
        val postOutputs = DigitalOutputs()
        postOutputs.relays = mutableListOf()
        var relay = Relay()
        relay.name = actuatorEntity.name
        relay.value = if (actuatorEntity.value == OnOffEntity.ON) "ON" else "OFF"
        postOutputs.relays.add(relay)


        val post = demeterApi.postActuators(session.getCmlId(), postOutputs)

        val response = post.execute()
        if (response.isSuccessful) {
            if (response.body() != null) {
                val eventsList = response.body()
                return getDemeterImage()
            }
        }
        return DemeterEntity(emptyList(), emptyList())
    }

    override fun getDemeterImage(): DemeterEntity {
        val call = demeterApi.getDemeter(session.getCmlId())
        val response = call.execute()
        if (response.isSuccessful) {
            if (response.body() != null) {
                val eventsList = response.body()
                if (eventsList != null) {
                    return entityMapper.mapFromRemote(eventsList)
                }
            }
        }
        return DemeterEntity(emptyList(), emptyList())
    }
}