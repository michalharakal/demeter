package org.buffer.android.boilerplate.presentation.mapper

import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.model.Actuator
import com.fiwio.iot.demeter.presentation.mapper.ActuatorNameMapper
import com.fiwio.iot.demeter.presentation.model.ActuatorState
import com.fiwio.iot.demeter.presentation.model.ActuatorView
import com.fiwio.iot.demeter.presentation.model.ActuatorViewState
import javax.inject.Inject

/**
 * Map a [ActuatorView] to and from a [Demeter] instance when data is moving between
 * this layer and the Domain layer
 */
open class ActuatorViewMapper @Inject constructor(val nameMapper: ActuatorNameMapper) : Mapper<ActuatorView, Actuator> {

    /**
     * Map a [Actuator] instance to a [ActuatorView] instance
     */
    override fun mapToView(type: Actuator): ActuatorView {
        val valueState = if (type.isOn) ActuatorState.ON else ActuatorState.OFF
        return ActuatorView(type.name, nameMapper.getUserNameFor(type.name), ActuatorViewState.ONLINE, valueState)
    }
}