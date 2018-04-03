package com.fiwio.iot.demeter.android.cache.mapper

import com.fiwio.iot.demeter.data.model.*
import com.fiwio.iot.demeter.domain.model.Actuator
import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.model.InputValue
import com.fiwio.iot.demeter.domain.model.Sensor
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
        return DemeterEntity(createActuatorEntityList(type.actuators), createSensorsEntityList(type.sensors))
    }


    private fun createSensorsEntityList(inputs: List<Sensor>): List<DigitalSensorEntity> {
        val result: MutableList<DigitalSensorEntity> = mutableListOf()
        inputs.forEach {
            result.add(DigitalSensorEntity(it.name, if (it.value == InputValue.ON) DigitalSensorValueEntity.ON else DigitalSensorValueEntity.OFF))
        }
        return result
    }

    private fun createActuatorEntityList(relays: List<Actuator>): List<ActuatorEntity> {
        val result: MutableList<ActuatorEntity> = mutableListOf()
        relays.forEach {
            result.add(ActuatorEntity(it.name, if (it.isOn) OnOffEntity.ON else OnOffEntity.OFF))
        }
        return result
    }
}