package com.fiwio.iot.demeter.remote.mapper

import com.fiwio.iot.demeter.data.model.*
import com.fiwo.iot.demeter.api.model.Demeter
import com.fiwo.iot.demeter.api.model.Input
import com.fiwo.iot.demeter.api.model.Relay
import javax.inject.Inject

/**
 * Map a [Demeter] to a [DemeterEntity] instance when data is moving between
 * this later and the Data layer
 */
open class DemeterEntityMapper @Inject constructor() : EntityMapper<Demeter, DemeterEntity> {

    /**
     * Map an instance of a [Demeter] to a [DemeterEntity] model
     */
    override fun mapFromRemote(type: Demeter): DemeterEntity {
        return DemeterEntity(createActuatorEntityList(type.relays), createSensorsEntityList(type.inputs))
    }


    private fun createSensorsEntityList(inputs: List<Input>): List<DigitalSensorEntity> {
        val result: MutableList<DigitalSensorEntity> = mutableListOf()
        inputs.forEach {
            result.add(DigitalSensorEntity(it.name, if (it.value.equals("ON")) DigitalSensorValueEntity.ON else DigitalSensorValueEntity.OFF))
        }
        return result
    }

    private fun createActuatorEntityList(relays: List<Relay>): List<ActuatorEntity> {
        val result: MutableList<ActuatorEntity> = mutableListOf()
        relays.forEach {
            result.add(ActuatorEntity(it.name, if (it.value.equals("ON")) OnOffEntity.ON else OnOffEntity.OFF))
        }
        return result
    }
}