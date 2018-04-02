package com.fiwio.iot.demeter.presentation.model


/**
 * Representation for a [ActuatorView] instance for this layers Model representation
 */
data class ActuatorView(val name: String, val caption: String, val viewState: ActuatorViewState, val state: ActuatorState)