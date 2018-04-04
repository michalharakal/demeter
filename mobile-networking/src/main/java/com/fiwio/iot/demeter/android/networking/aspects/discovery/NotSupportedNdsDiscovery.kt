package com.fiwio.iot.demeter.android.networking.aspects.discovery

import android.os.Handler

class NotSupportedNdsDiscovery : MulticastDns {
    override fun discoverServices(demeterServiceFinder: HandleDemeterServiceSearch) {
        demeterServiceFinder.onServiceSearchFailed()
    }

    override fun stopDiscovery() {
    }
}