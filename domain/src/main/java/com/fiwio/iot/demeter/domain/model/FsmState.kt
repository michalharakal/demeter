package com.fiwio.iot.demeter.domain.model

enum class FsmState(stateName: String) {
    CLOSED("CLOSED"),
    IRRIGATION_OPENING("IRRIGATION_OPENING"),
    IRRIGATING("IRRIGATING"),
    BARREL_FILLING_OPENING("BARREL_FILLING_OPENING"),
    BARREL_FILLING("BARREL_FILLING"),
    CLOSING("CLOSING")
}
