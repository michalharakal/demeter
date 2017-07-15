package com.fiwio.iot.demeter.discovery;

interface ClientNetworkListener {
    void onServiceDiscoveryError();

    void onServiceDiscovered(HostInfo hostInfo);
}
