package com.fiwio.iot.demeter.data.source

import com.fiwio.iot.demeter.data.repository.DemeterCache
import com.fiwio.iot.demeter.data.repository.DemeterDataStore
import com.fiwio.iot.demeter.domain.model.Demeter
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of the [DemeterDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class DemeterCacheDataStore @Inject constructor(private val demeterCache: DemeterCache) :
        DemeterDataStore {
    override fun getDemeterImage(): Single<Demeter> {
        return demeterCache.getDemeterImage()
    }
}
