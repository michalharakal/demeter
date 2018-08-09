package com.fiwio.iot.demeter.domain.model

data class ScheduledAction(val name: String, val branch: String, val command: String, val dayTime: DayTime)