package com.fiwio.iot.demeter.domain.model

data class ScheduledActionsWithState(val actions: List<ScheduledAction>, val fsmStates: List<Fsm>)