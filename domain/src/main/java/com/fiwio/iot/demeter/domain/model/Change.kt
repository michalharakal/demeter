package com.fiwio.iot.demeter.domain.model

enum class DataChange {
    ANY, ACTUATOR, SENSOR, TASK, STATE
}

data class Change(val dataChange: DataChange)