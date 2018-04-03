package com.fiwio.iot.demeter.data.mapper

import com.fiwio.iot.demeter.data.model.ActuatorEntity
import com.fiwio.iot.demeter.data.model.OnOffEntity
import com.fiwio.iot.demeter.domain.model.Actuator
import org.dukecon.data.mapper.Mapper
import javax.inject.Inject


/**
 * Map a [ActuatorEntity] to and from a [Actuator] instance when data is moving between
 * this later and the Domain layer
 */
open class ActuatorMapper @Inject constructor() : Mapper<ActuatorEntity, Actuator> {
    override fun mapFromEntity(type: ActuatorEntity): Actuator {
        return Actuator(type.name, type.value == OnOffEntity.ON)
    }

    override fun mapToEntity(type: Actuator): ActuatorEntity {
        return ActuatorEntity(type.name, if (type.isOn) OnOffEntity.ON else OnOffEntity.OFF)
    }
}
