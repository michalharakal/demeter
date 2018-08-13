package com.fiwio.iot.demeter.android.cache

import com.fiwio.iot.demeter.android.cache.persistance.DemeterCacheSerializer
import com.fiwio.iot.demeter.data.model.DemeterEntity
import com.fiwio.iot.demeter.data.model.FsmListEnitities
import com.fiwio.iot.demeter.data.model.ScheduledActionsEntity
import com.fiwio.iot.demeter.data.repository.DemeterCache
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import mu.KotlinLogging
import javax.inject.Inject

private val logger = KotlinLogging.logger {}

class DemeterGsonCache @Inject constructor(val demeterCacheGsonSerializer: DemeterCacheSerializer,
                                           val preferencesHelper: PreferencesHelper) : DemeterCache {

    var demeter: DemeterEntity = DemeterEntity(emptyList(), emptyList())
    var scheduledActions: ScheduledActionsEntity = ScheduledActionsEntity(emptyList())

    init {
        if (preferencesHelper.lastCacheTime > 0) {
            logger.info { "cold start, reading data from disc cache" }
            demeterCacheGsonSerializer.let {
                demeter = it.readDemeter()
            }
        }
    }

    // 10 sec cache
    private val EXPIRATION_TIME = (10 * 1000).toLong()


    override fun isCached(): Boolean {
        return demeter.actuators.isNotEmpty()
    }

    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = System.currentTimeMillis()
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = preferencesHelper.lastCacheTime

        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    override fun invalidate() {
        demeter = DemeterEntity(emptyList(), emptyList())
    }

    override fun saveDemeterImage(demeter: DemeterEntity): Completable {
        this.demeter = demeter
        demeterCacheGsonSerializer.writeDemeter(demeter)
        preferencesHelper.lastCacheTime = System.currentTimeMillis()
        return Completable.complete()
    }

    override fun getDemeterImage(): Single<DemeterEntity> {
        return Single.just(demeterCacheGsonSerializer.readDemeter())
    }

    override fun getScheduledActions(): Observable<ScheduledActionsEntity> {
        return Observable.fromCallable { demeterCacheGsonSerializer.readScheduledActions() }
    }

    override fun getFsm(): Observable<FsmListEnitities> {
        return Observable.fromCallable { demeterCacheGsonSerializer.readFsmStatus() }

    }


    override fun saveScheduledActions(scheduledActions: ScheduledActionsEntity): Completable {
        this.scheduledActions = scheduledActions
        demeterCacheGsonSerializer.writeScheduledActions(scheduledActions)
        return Completable.complete()

    }
}

