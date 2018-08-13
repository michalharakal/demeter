package com.fiwio.iot.demeter.presentation.model

data class ScheduledActionModel(val name: String, val branch: String, val command: String, val dayTime: String, val status: FsmStateModel, val isActiveNow:Boolean)