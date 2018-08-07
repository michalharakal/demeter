package com.fiwio.iot.demeter.remote.source

import com.fiwio.iot.demeter.data.model.ActuatorEntity
import com.fiwio.iot.demeter.data.model.DemeterEntity
import com.fiwio.iot.demeter.data.model.OnOffEntity
import com.fiwio.iot.demeter.data.repository.DemeterRemote
import com.fiwio.iot.demeter.remote.mapper.DemeterEntityMapper
import com.fiwo.iot.demeter.api.DefaultApi
import com.fiwo.iot.demeter.api.model.DigitalOutputs
import com.fiwo.iot.demeter.api.model.Relay
import io.reactivex.Single
import io.reactivex.SingleEmitter
import javax.inject.Inject


/**
 * Remote implementation for retrieving Event instances. This class implements the
 * [EventRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class DemeterRemoteImpl @Inject constructor(private val demeterApi: DefaultApi,
                                            private val entityMapper: DemeterEntityMapper) :
        DemeterRemote {
    override fun switchActuator(actuatorEntity: ActuatorEntity): Single<DemeterEntity> {
        return Single.create { s ->

            // create list with one actuator
            val postOutputs = DigitalOutputs()
            postOutputs.relays = mutableListOf()
            var relay = Relay()
            relay.name = actuatorEntity.name
            relay.value = if (actuatorEntity.value == OnOffEntity.ON) "ON" else "OFF"
            postOutputs.relays.add(relay)


            val post = demeterApi.demeterPost(postOutputs)

            val response = post.execute()
            if (response.isSuccessful) {
                if (response.body() != null) {
                    val eventsList = response.body()
                    if (eventsList != null) {
                        getDemeterImageCall(s)
                    }
                }
            } else {
                s.onError(Throwable())
            }
        }

    }

    override fun getDemeterImage(): Single<DemeterEntity> {
        return Single.create { s ->
            getDemeterImageCall(s)
        }
    }

    private fun getDemeterImageCall(s: SingleEmitter<DemeterEntity>) {
        val call = demeterApi.demeterGet()
        val response = call.execute()
        if (response.isSuccessful) {
            if (response.body() != null) {
                val eventsList = response.body()
                if (eventsList != null) {
                    s.onSuccess(
                            entityMapper.mapFromRemote(eventsList)
                    )
                }
            }
        } else {
            s.onError(Throwable())
        }
    }
}