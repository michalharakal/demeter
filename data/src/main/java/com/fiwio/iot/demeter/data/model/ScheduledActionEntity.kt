package com.fiwio.iot.demeter.data.model

import com.fiwio.iot.demeter.domain.model.DayTime

data class ScheduledActionEntity(val name: String, val branch: String, val command: String, val dayTime: DayTime)