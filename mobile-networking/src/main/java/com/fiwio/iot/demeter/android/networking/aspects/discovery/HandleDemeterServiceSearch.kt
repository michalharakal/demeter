package com.fiwio.iot.demeter.android.networking.aspects.discovery

interface HandleDemeterServiceSearch {
    fun onServiceFound(ip: String)
    fun onServiceSearchFailed()
}
