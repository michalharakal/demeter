package com.fiwio.iot.demeter.domain.repository

import com.fiwio.iot.demeter.domain.model.DemeterSearchDnsInfo
import io.reactivex.Observable


interface DemeterFinder {
    fun findDemeterUrl(): Observable<DemeterSearchDnsInfo>
}

