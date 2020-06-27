package com.fiwio.iot.demeter.remote.firebase.remote.firebase.mapper

import com.fiwio.iot.demeter.data.model.FsmEntity
import com.fiwio.iot.demeter.data.model.FsmListEnitities
import com.fiwio.iot.demeter.domain.model.FsmState
import com.fiwo.iot.demeter.api.model.StateMachine
import com.fiwo.iot.demeter.api.model.StateMachines
import javax.inject.Inject

class FsmEntityMapper @Inject constructor() : EntityMapper<StateMachines, FsmListEnitities> {
    override fun mapFromRemote(type: StateMachines): FsmListEnitities {
        return FsmListEnitities(type.branches.map { mapToEntity(it) })
    }

    private fun mapToEntity(it: StateMachine): FsmEntity {
        return FsmEntity(it.branch, stateFromString(it.state))
    }

    private fun stateFromString(state: String): FsmState {
        when (state) {
            "CLOSED" -> return FsmState.CLOSED
            "IRRIGATION_OPENING" -> return FsmState.IRRIGATION_OPENING
            "IRRIGATING" -> return FsmState.IRRIGATING
            "BARREL_FILLING_OPENING" -> return FsmState.BARREL_FILLING_OPENING
            "BARREL_FILLING" -> return FsmState.BARREL_FILLING
            "CLOSING" -> return FsmState.CLOSING
            else -> return FsmState.CLOSED
        }
    }
}