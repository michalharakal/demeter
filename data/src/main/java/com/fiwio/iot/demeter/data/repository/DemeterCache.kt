package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.model.DemeterEntity
import io.reactivex.Completable
import io.reactivex.Single

interface DemeterCache {
    fun getDemeterImage(): Single<DemeterEntity>
    fun isCached(): Boolean
    fun isExpired(): Boolean
    fun saveDemeterImage(demeter: DemeterEntity): Completable
    fun setLastCacheTime(lastCache: Long)
    fun invalidate()
}