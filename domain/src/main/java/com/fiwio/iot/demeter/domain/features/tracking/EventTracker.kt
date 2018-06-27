package com.fiwio.iot.demeter.domain.features.tracking

interface EventTracker {
    fun track(event: String)
}