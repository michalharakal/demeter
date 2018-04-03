package com.fiwio.iot.demeter.data.source

import com.fiwio.iot.demeter.data.repository.DemeterCache
import com.fiwio.iot.demeter.data.repository.DemeterDataStore
import com.fiwio.iot.demeter.domain.connectivity.ConnectivityState
import javax.inject.Inject


/**
 * Create an instance of a EventDataStore
 */
open class DemeterDataSourceFactory @Inject constructor(
        private val connectivityState: ConnectivityState,
        private val demeterCache: DemeterCache,
        private val demeterCacheDataStore: DemeterCacheDataStore,
        private val demeterRemoteDataStore:DemeterRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(): DemeterDataStore {
        // has valid cacched data, or if no internet has any data
        if ((demeterCache.isCached() && !demeterCache.isExpired()) ||(demeterCache.isCached() && connectivityState.isOffline)) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveCacheDataStore(): DemeterDataStore {
        return demeterCacheDataStore
    }

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveRemoteDataStore(): DemeterDataStore {
        return demeterRemoteDataStore
    }

}
