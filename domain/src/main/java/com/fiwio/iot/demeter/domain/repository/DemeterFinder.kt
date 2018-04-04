package com.fiwio.iot.demeter.domain.repository

import com.fiwio.iot.demeter.domain.model.DemeterSearchDnsInfo
import io.reactivex.Single


interface DemeterFinder {
    fun findDemeterUrl(): Single<DemeterSearchDnsInfo>
}

