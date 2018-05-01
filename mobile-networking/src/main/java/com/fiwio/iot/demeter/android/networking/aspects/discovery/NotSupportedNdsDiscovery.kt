package com.fiwio.iot.demeter.android.networking.aspects.discovery

import android.os.Handler

class NotSupportedNdsDiscovery : MulticastDns {
    override fun discoverServices(demeterServiceFinder: HandleDemeterServiceSearch, handler: Handler) {
        demeterServiceFinder.onServiceSearchFailed()
    }

    override fun stopDiscovery() {
    }
}