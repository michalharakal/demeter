package com.fiwio.iot.demeter.presentation.mapper

import com.fiwio.iot.demeter.domain.model.ScheduledActions
import com.fiwio.iot.demeter.presentation.model.ScheduledActionModel
import com.fiwio.iot.demeter.presentation.model.ScheduledActionsModel
import org.buffer.android.boilerplate.presentation.mapper.Mapper
import javax.inject.Inject

open class ScheduledActionMapper @Inject constructor(val stringsProvider: ScheduleStringsProvider) : Mapper<ScheduledActionsModel, ScheduledActions> {
    override fun mapToView(type: ScheduledActions): ScheduledActionsModel {
        return ScheduledActionsModel(type.actions
                .map { action ->
                    ScheduledActionModel(
                            stringsProvider.getBranchUserName(action.name),
                            stringsProvider.getCommnadUserName(action.command),
                            "${action.dayTime.hour}:${action.dayTime.minute}:${action.dayTime.second}"
                    )
                })
    }

}