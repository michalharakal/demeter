package com.fiwio.iot.demeter.presentation.model

enum class FsmStateModel(stateName: String) {
    CLOSED("CLOSED"),
    IRRIGATION_OPENING("IRRIGATION_OPENING"),
    IRRIGATING("IRRIGATING"),
    BARREL_FILLING_OPENING("BARREL_FILLING_OPENING"),
    BARREL_FILLING("BARREL_FILLING"),
    CLOSING("CLOSING")
}
