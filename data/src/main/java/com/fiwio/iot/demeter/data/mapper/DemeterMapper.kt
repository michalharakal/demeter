package org.dukecon.data.mapper

import com.fiwio.iot.demeter.data.mapper.ActuatorMapper
import com.fiwio.iot.demeter.data.model.*
import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.model.Sensor
import com.fiwio.iot.demeter.domain.model.InputValue
import com.fiwio.iot.demeter.domain.model.Actuator
import javax.inject.Inject

/**
 * Map a [DemeterEntity] to and from a [Demeter] instance when data is moving between
 * this later and the Domain layer
 */
class DemeterMapper @Inject constructor(val actuatorMapper: ActuatorMapper) : Mapper<DemeterEntity, Demeter> {

    /**
     * Map a [DemeterEntity] instance to a [Demeter] instance
     */
    override fun mapFromEntity(type: DemeterEntity): Demeter {
        return Demeter(createActuatorList(type.actuators), createSensorsList(type.sensors))
    }

    private fun createSensorsList(inputs: List<DigitalSensorEntity>): List<Sensor> {
        val result: MutableList<Sensor> = mutableListOf()
        inputs.forEach {
            result.add(Sensor(it.name, if (it.value == DigitalSensorValueEntity.ON) InputValue.ON else InputValue.OFF))
        }
        return result
    }

    private fun createActuatorList(relays: List<ActuatorEntity>): List<Actuator> {
        val result: MutableList<Actuator> = mutableListOf()
        relays.forEach {
            result.add(actuatorMapper.mapFromEntity(it))
        }
        return result
    }

    /**
     * Map a [Demeter] instance to a [DemeterEntity] instance
     */
    override fun mapToEntity(type: Demeter): DemeterEntity {
        return DemeterEntity(createActuatorEntityList(type.actuators), createSensorsEntityList(type.sensors))
    }

    private fun createSensorsEntityList(sensors: List<Sensor>): List<DigitalSensorEntity> {
        val result: MutableList<DigitalSensorEntity> = mutableListOf()
        sensors.forEach {
            result.add(DigitalSensorEntity(it.name, if (it.value == InputValue.ON) DigitalSensorValueEntity.ON else DigitalSensorValueEntity.OFF))
        }
        return result
    }

    private fun createActuatorEntityList(actuators: List<Actuator>): List<ActuatorEntity> {
        val result: MutableList<ActuatorEntity> = mutableListOf()
        actuators.forEach {
            result.add(actuatorMapper.mapToEntity(it))
        }
        return result
    }
}