package com.fiwio.iot.demeter.data.model

data class DemeterEntity(
        val actuators: List<ActuatorEntity>,
        val sensors: List<DigitalSensorEntity>
)
