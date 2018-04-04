package com.fiwio.iot.demeter.android.networking.aspects.discovery

import java.net.InetAddress

class HostInfo(val name: String, val address: InetAddress, val port: Int) {


    override fun toString(): String {
        return name
    }

}