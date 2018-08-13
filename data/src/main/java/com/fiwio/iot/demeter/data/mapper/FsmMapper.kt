package com.fiwio.iot.demeter.data.mapper

import com.fiwio.iot.demeter.data.model.FsmEntity
import com.fiwio.iot.demeter.data.model.FsmListEnitities
import com.fiwio.iot.demeter.domain.model.Fsm
import com.fiwio.iot.demeter.domain.model.FsmList
import org.dukecon.data.mapper.Mapper
import javax.inject.Inject

class FsmMapper @Inject constructor() : Mapper<FsmListEnitities, FsmList> {
    override fun mapFromEntity(type: FsmListEnitities): FsmList {
        return FsmList(type.entities.map { mapFromFsmEntity(it) })
    }

    private fun mapFromFsmEntity(it: FsmEntity): Fsm {
        return Fsm(it.name, it.fsmState)
    }

    override fun mapToEntity(type: FsmList): FsmListEnitities {
        return FsmListEnitities(type.fsm.map { mapToFsmEntity(it) })
    }

    private fun mapToFsmEntity(it: Fsm): FsmEntity {
        return FsmEntity(it.name, it.state)
    }
}