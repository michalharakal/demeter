package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.domain.model.Demeter
import io.reactivex.Single

interface DemeterDataStore {
    fun getDemeterImage(): Single<Demeter>
}