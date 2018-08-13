package com.fiwio.iot.demeter.domain.features.schedule

import com.fiwio.iot.demeter.domain.model.DayTime


interface TimeProvider {
    fun getCurrentTime(): DayTime
}