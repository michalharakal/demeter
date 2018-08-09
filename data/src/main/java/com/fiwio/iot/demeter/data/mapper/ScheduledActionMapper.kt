package com.fiwio.iot.demeter.data.mapper

import com.fiwio.iot.demeter.data.model.ScheduledActionEntity
import com.fiwio.iot.demeter.data.model.ScheduledActionsEntity
import com.fiwio.iot.demeter.domain.model.ScheduledAction
import com.fiwio.iot.demeter.domain.model.ScheduledActions
import org.dukecon.data.mapper.Mapper
import javax.inject.Inject

class ScheduledActionMapper @Inject constructor() : Mapper<ScheduledActionsEntity, ScheduledActions> {
    override fun mapFromEntity(type: ScheduledActionsEntity): ScheduledActions {
        return ScheduledActions(type.actions.map {
            mapFromEntity(it)
        })
    }

    override fun mapToEntity(type: ScheduledActions): ScheduledActionsEntity {
        return ScheduledActionsEntity(type.actions.map {
            mapToEntity(it)
        })
    }

    fun mapFromEntity(type: ScheduledActionEntity): ScheduledAction {
        return ScheduledAction(type.name, type.branch, type.command, type.dayTime)
    }

    fun mapToEntity(type: ScheduledAction): ScheduledActionEntity {
        return ScheduledActionEntity(type.name, type.branch, type.command, type.dayTime)
    }
}