package com.fiwio.iot.demeter.android.networking.aspects.discovery

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Build
import android.os.Handler
import android.support.annotation.RequiresApi
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
class NdsDiscovery(context: Context) : NsdManager.DiscoveryListener, MulticastDns {


    private val nsdManager: NsdManager

    private lateinit var handler: Handler

    init {
        nsdManager = context.getSystemService(Context.NSD_SERVICE) as NsdManager
    }

    private lateinit var searchHandler: HandleDemeterServiceSearch

    override fun discoverServices(demeterServiceFinder: HandleDemeterServiceSearch, handler: Handler) {
        try {
            this.handler = handler
            this.searchHandler = demeterServiceFinder
            nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, this)
        } catch (e: IllegalArgumentException) {
            demeterServiceFinder.onServiceSearchFailed()
        }
    }

    override fun stopDiscovery() {
        try {
            nsdManager.stopServiceDiscovery(this)
        } catch (e: IllegalArgumentException) {
            searchHandler.onServiceSearchFailed()
        }

    }

    override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
        logger.debug { "onStartDiscoveryFailed failed$serviceType" }
        searchHandler.onServiceSearchFailed()
    }

    override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
        logger.debug { "onStopDiscoveryFailed failed$serviceType" }
        searchHandler.onServiceSearchFailed()
    }

    override fun onDiscoveryStarted(serviceType: String) {
        logger.debug { "onDiscoveryStarted $serviceType" }
    }

    override fun onDiscoveryStopped(serviceType: String) {
        logger.debug { "onDiscoveryStopped$serviceType" }
    }

    override fun onServiceFound(serviceInfo: NsdServiceInfo) {
        logger.debug { " onServiceFound ${serviceInfo.serviceName}" }
        if (serviceInfo.serviceType != SERVICE_TYPE || !serviceInfo.serviceName.startsWith(SERVICE_PREFIX)) {
            logger.debug { "Discarding discovered service" }
            return
        }
        val resolveListener = ResolveListener(handler, object : ClientNetworkListener {

            override fun onServiceDiscoveryError() {
                this@NdsDiscovery.searchHandler.onServiceSearchFailed()
            }

            override fun onServiceDiscovered(hostInfo: HostInfo) {
                this@NdsDiscovery.searchHandler.onServiceFound("http://" + hostInfo.address
                        .getHostAddress() + ":" + hostInfo.port)
            }
        })
        nsdManager.resolveService(serviceInfo, resolveListener)
    }

    override fun onServiceLost(serviceInfo: NsdServiceInfo) {
        searchHandler.onServiceSearchFailed()
    }

    companion object {
        private val SERVICE_PREFIX = "cml"
        private val SERVICE_TYPE = "_socket._tcp."
    }
}

