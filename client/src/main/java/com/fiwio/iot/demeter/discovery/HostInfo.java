package com.fiwio.iot.demeter.discovery;

import java.net.InetAddress;

public final class HostInfo {

    private final String name;
    private final InetAddress address;
    private final int port;

    public HostInfo(String name, InetAddress address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;
    }


    public String getName() {
        return name;
    }


    public InetAddress getAddress() {
        return address;
    }


    public int getPort() {
        return port;
    }


    @Override
    public String toString() {
        return name;
    }

}
