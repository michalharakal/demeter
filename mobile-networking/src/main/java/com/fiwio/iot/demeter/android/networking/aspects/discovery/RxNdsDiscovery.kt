package com.fiwio.iot.demeter.android.networking.aspects.discovery

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import com.fiwio.iot.demeter.domain.model.DemeterSearchDnsInfo
import com.fiwio.iot.demeter.domain.model.DnsSearchResult
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
class RxNdsDiscovery(val context: Context) : HandleDemeterServiceSearch {

    private var relay: PublishRelay<DemeterSearchDnsInfo> = PublishRelay.create<DemeterSearchDnsInfo>()

    private val ndsDicscovery = NdsDiscovery(context)

    fun startSearch(): Observable<DemeterSearchDnsInfo> {
        ndsDicscovery.discoverServices(this)
        return relay
    }

    override fun onServiceFound(ip: String) {
        relay.accept(DemeterSearchDnsInfo(DnsSearchResult.FOUND, ip))
    }

    override fun onServiceSearchFailed() {
        relay.accept(DemeterSearchDnsInfo(DnsSearchResult.NOT_FOUND))
    }
}