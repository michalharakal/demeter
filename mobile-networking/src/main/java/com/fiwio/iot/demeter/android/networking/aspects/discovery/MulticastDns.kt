package com.fiwio.iot.demeter.android.networking.aspects.discovery

import android.os.Handler

interface MulticastDns {
    fun discoverServices(demeterServiceFinder: HandleDemeterServiceSearch, handler: Handler)
    fun stopDiscovery()
}
