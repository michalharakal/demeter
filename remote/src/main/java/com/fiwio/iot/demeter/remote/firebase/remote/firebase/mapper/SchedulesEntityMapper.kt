package com.fiwio.iot.demeter.remote.firebase.remote.firebase.mapper

import com.fiwio.iot.demeter.data.model.ScheduledActionEntity
import com.fiwio.iot.demeter.data.model.ScheduledActionsEntity
import com.fiwo.iot.demeter.api.model.DayTime
import com.fiwo.iot.demeter.api.model.ScheduledEvent
import com.fiwo.iot.demeter.api.model.ScheduledEvents
import javax.inject.Inject

class SchedulesEntityMapper @Inject constructor() : EntityMapper<ScheduledEvents, ScheduledActionsEntity> {
    override fun mapFromRemote(type: ScheduledEvents): ScheduledActionsEntity {
        return ScheduledActionsEntity(type.map { map(it) })
    }

    private fun map(event: ScheduledEvent): ScheduledActionEntity {
        return ScheduledActionEntity(event.name, event.fsm, event.command, mapDate(event.time))

    }

    private fun mapDate(time: DayTime): com.fiwio.iot.demeter.domain.model.DayTime {
        return com.fiwio.iot.demeter.domain.model.DayTime(time.hour, time.minute, time.second)
    }
}