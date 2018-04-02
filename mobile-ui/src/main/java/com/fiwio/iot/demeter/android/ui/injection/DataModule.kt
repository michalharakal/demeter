package com.fiwio.iot.demeter.android.ui.injection

import android.app.Application
import android.content.Context
import com.fiwio.iot.demeter.android.cache.DemeterGsonCache
import com.fiwio.iot.demeter.android.cache.persistance.DemeterCacheGsonSerializer
import com.fiwio.iot.demeter.android.ui.app.AppResourcesActuatorNameMapper
import com.fiwio.iot.demeter.data.repository.DemeterCache
import com.fiwio.iot.demeter.data.repository.DemeterDataRepository
import com.fiwio.iot.demeter.data.source.DemeterDataSourceFactory
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import com.fiwio.iot.demeter.presentation.mapper.ActuatorNameMapper
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataModule {

    @Provides
    @Singleton
    internal fun provideActuatorNameMapper(context: Context): ActuatorNameMapper {
        return AppResourcesActuatorNameMapper(context)
    }

    @Provides
    @Singleton
    internal fun provideDemeterCache(application: Application, gson: Gson): DemeterCache {
        val baseCacheFolder = application.filesDir.absolutePath
        return DemeterGsonCache(DemeterCacheGsonSerializer(baseCacheFolder, gson))
    }

    @Provides
    @Singleton
    internal fun provideEventRepository(demeterDataSourceFactory: DemeterDataSourceFactory): DemeterRepository {
        return DemeterDataRepository(demeterDataSourceFactory)
    }
}