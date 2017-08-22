package com.fiwio.iot.demeter.app.demeter.discovery;

interface ClientNetworkListener {
    void onServiceDiscoveryError();

    void onServiceDiscovered(HostInfo hostInfo);
}
