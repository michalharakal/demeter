package com.fiwio.iot.demeter.domain.model

data class Demeter(
        val actuators: List<Actuator>,
        val sensors: List<Sensor>
)

