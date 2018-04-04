package com.fiwio.iot.demeter.android.networking.aspects.discovery


interface ClientNetworkListener {
    fun onServiceDiscoveryError()

    fun onServiceDiscovered(hostInfo: HostInfo)
}
