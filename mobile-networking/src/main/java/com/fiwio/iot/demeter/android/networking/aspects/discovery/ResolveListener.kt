package com.fiwio.iot.demeter.android.networking.aspects.discovery

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Build
import android.os.Handler
import android.support.annotation.RequiresApi
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}


@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
class ResolveListener(private val handler: Handler, private val clientNetworkListener: ClientNetworkListener) : NsdManager.ResolveListener {

    override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
        logger.error { "Service resolve error ($errorCode)" }
        handler.post { clientNetworkListener.onServiceDiscoveryError() }
    }

    override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
        logger.debug { "Service resolve success ($serviceInfo)" }
        handler.post {
            clientNetworkListener.onServiceDiscovered(
                    HostInfo(
                            serviceInfo.serviceName,
                            serviceInfo.host,
                            serviceInfo.port))
        }
    }
}
