package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.model.DemeterEntity
import com.fiwio.iot.demeter.data.model.FsmListEnitities
import com.fiwio.iot.demeter.data.model.ScheduledActionsEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface DemeterCache {
    fun isCached(): Boolean
    fun isExpired(): Boolean
    fun setLastCacheTime(lastCache: Long)
    fun invalidate()

    fun getDemeterImage(): DemeterEntity
    fun saveDemeterImage(demeter: DemeterEntity)

    fun getScheduledActionsEntities(): ScheduledActionsEntity
    fun saveScheduledActions(scheduledActions: ScheduledActionsEntity)

    fun getFsm(): FsmListEnitities
    fun saveFsmEntities(fsmListEnitities: FsmListEnitities)
}