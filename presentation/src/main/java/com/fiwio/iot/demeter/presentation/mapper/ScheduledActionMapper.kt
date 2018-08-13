package com.fiwio.iot.demeter.presentation.mapper

import com.fiwio.iot.demeter.domain.features.schedule.TimeProvider
import com.fiwio.iot.demeter.domain.model.*
import com.fiwio.iot.demeter.presentation.model.FsmStateModel
import com.fiwio.iot.demeter.presentation.model.ScheduledActionModel
import com.fiwio.iot.demeter.presentation.model.ScheduledActionsModel
import java.util.*
import javax.inject.Inject

fun Int.format(digits: Int) = java.lang.String.format("%.${digits}d", this)

open class ScheduledActionMapper @Inject constructor(val stringsProvider: ScheduleStringsProvider,
                                                     val timeProvider: TimeProvider) {
    fun mapToView(type: ScheduledActionsWithState): ScheduledActionsModel {
        return ScheduledActionsModel(type.actions
                .sortedBy { it.dayTime }
                .map { action ->
                    ScheduledActionModel(
                            action.name,
                            stringsProvider.getBranchUserName(action.branch),
                            stringsProvider.getCommnadUserName(action.command),
                            String.format("%02d:%02d:%02d", action.dayTime.hour,action.dayTime.minute,action.dayTime.second),
                            getFsmState(action.branch, type.fsmStates),
                            isArroundNow(action, type.actions, timeProvider.getCurrentTime()) && isActive(getFsmState(action.branch, type.fsmStates))
                    )
                })
    }

    private fun isActive(status: FsmStateModel): Boolean {
        return when (status) {
            FsmStateModel.BARREL_FILLING -> true
            FsmStateModel.BARREL_FILLING_OPENING -> true
            FsmStateModel.IRRIGATING -> true
            FsmStateModel.IRRIGATION_OPENING -> true
            else -> false
        }
    }

    private fun isArroundNow(action: ScheduledAction, actions: List<ScheduledAction>, now: DayTime): Boolean {
        /*
        Collections.sort(actions, object : Comparator<ScheduledAction> {
            override fun compare(x: ScheduledAction, y: ScheduledAction) = y.compareTo(x)
        })
        */
        return true
    }

    private fun getFsmState(branch: String, fsmStates: List<Fsm>): FsmStateModel {
        val fsmState = fsmStates.first { it.name.contains(branch) }
        return mapFsmState(fsmState.state)
    }

    private fun mapFsmState(fsmState: FsmState): FsmStateModel {
        return when (fsmState) {
            FsmState.CLOSED -> FsmStateModel.CLOSED
            FsmState.IRRIGATION_OPENING -> FsmStateModel.IRRIGATION_OPENING
            FsmState.IRRIGATING -> FsmStateModel.IRRIGATING
            FsmState.BARREL_FILLING_OPENING -> FsmStateModel.BARREL_FILLING_OPENING
            FsmState.BARREL_FILLING -> FsmStateModel.BARREL_FILLING
            FsmState.CLOSING -> FsmStateModel.CLOSING
            else -> FsmStateModel.CLOSED
        }
    }
}