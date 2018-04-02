package com.fiwio.iot.demeter.android.cache

import com.fiwio.iot.demeter.android.cache.persistance.DemeterCacheGsonSerializer
import com.fiwio.iot.demeter.android.cache.persistance.DemeterCacheSerializer
import com.fiwio.iot.demeter.data.repository.DemeterCache
import com.fiwio.iot.demeter.domain.model.Demeter
import io.reactivex.Single
import javax.inject.Inject

class DemeterGsonCache @Inject constructor(val demeterCacheGsonSerializer: DemeterCacheSerializer) : DemeterCache {
    override fun getDemeterImage(): Single<Demeter> {
        return Single.just(demeterCacheGsonSerializer.readDemeter())
    }

}

