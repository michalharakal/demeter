package org.buffer.android.boilerplate.presentation.mapper

import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.model.Relay
import com.fiwio.iot.demeter.presentation.mapper.ActuatorNameMapper
import com.fiwio.iot.demeter.presentation.model.ActuatorState
import com.fiwio.iot.demeter.presentation.model.ActuatorView
import com.fiwio.iot.demeter.presentation.model.ActuatorViewState
import javax.inject.Inject

/**
 * Map a [ActuatorView] to and from a [Demeter] instance when data is moving between
 * this layer and the Domain layer
 */
open class ActuatorViewMapper @Inject constructor(val nameMapper: ActuatorNameMapper) : Mapper<ActuatorView, Relay> {

    /**
     * Map a [Relay] instance to a [ActuatorView] instance
     */
    override fun mapToView(type: Relay): ActuatorView {
        val valueState = if (type.value) ActuatorState.ON else ActuatorState.OFF
        return ActuatorView(type.name, nameMapper.getUserNameFor(type.name), ActuatorViewState.ONLINE, valueState)
    }
}