package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.model.DemeterEntity
import com.fiwio.iot.demeter.data.model.ScheduledActionsEntity
import io.reactivex.Completable
import io.reactivex.Single

interface DemeterCache {
    fun getDemeterImage(): Single<DemeterEntity>
    fun isCached(): Boolean
    fun isExpired(): Boolean
    fun saveDemeterImage(demeter: DemeterEntity): Completable
    fun setLastCacheTime(lastCache: Long)
    fun invalidate()
    fun getScheduledActions(): Single<ScheduledActionsEntity>
    fun saveScheduledActions(scheduledActions: ScheduledActionsEntity): Completable
}