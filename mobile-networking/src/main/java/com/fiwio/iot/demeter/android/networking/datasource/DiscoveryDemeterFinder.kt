package com.fiwio.iot.demeter.android.networking.datasource

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import com.fiwio.iot.demeter.android.networking.aspects.discovery.RxNdsDiscovery
import com.fiwio.iot.demeter.domain.model.DemeterSearchDnsInfo
import com.fiwio.iot.demeter.domain.repository.DemeterFinder
import io.reactivex.Single

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
class DiscoveryDemeterFinder(val context: Context) : DemeterFinder {

    var rxNdsDiscovery: RxNdsDiscovery = RxNdsDiscovery(context)

    override fun findDemeterUrl(): Single<DemeterSearchDnsInfo> {
        val observable = rxNdsDiscovery.startSearch()
        return Single.just(observable.blockingFirst())
    }
}