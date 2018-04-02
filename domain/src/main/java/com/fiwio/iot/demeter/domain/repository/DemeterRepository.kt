package com.fiwio.iot.demeter.domain.repository

import com.fiwio.iot.demeter.domain.model.Demeter
import io.reactivex.Single

interface DemeterRepository {
    fun getDemeterImage(): Single<Demeter>
}