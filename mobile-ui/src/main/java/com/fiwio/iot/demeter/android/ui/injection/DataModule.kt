package com.fiwio.iot.demeter.android.ui.injection

import android.app.Application
import android.content.Context
import com.fiwio.iot.demeter.android.cache.DemeterGsonCache
import com.fiwio.iot.demeter.android.cache.PreferencesHelper
import com.fiwio.iot.demeter.android.cache.persistance.DemeterCacheGsonSerializer
import com.fiwio.iot.demeter.android.ui.app.AppResourcesActuatorNameMapper
import com.fiwio.iot.demeter.data.mapper.ActuatorMapper
import com.fiwio.iot.demeter.data.repository.DemeterCache
import com.fiwio.iot.demeter.data.repository.DemeterDataRepository
import com.fiwio.iot.demeter.data.repository.DemeterRemote
import com.fiwio.iot.demeter.data.source.DemeterDataSourceFactory
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import com.fiwio.iot.demeter.presentation.mapper.ActuatorNameMapper
import com.fiwio.iot.demeter.remote.mapper.DemeterEntityMapper
import com.fiwio.iot.demeter.remote.source.DemeterRemoteImpl
import com.fiwo.iot.demeter.api.DefaultApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import org.dukecon.data.mapper.DemeterMapper
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
    internal fun provideDemeterCache(application: Application, gson: Gson,
                                     preferencesHelper: PreferencesHelper): DemeterCache {
        val baseCacheFolder = application.filesDir.absolutePath
        return DemeterGsonCache(DemeterCacheGsonSerializer(baseCacheFolder, gson), preferencesHelper)
    }

    @Provides
    @Singleton
    internal fun provideEventRemote(service: DefaultApi, entityMapper: DemeterEntityMapper): DemeterRemote {
        return DemeterRemoteImpl(service, entityMapper)
    }

    @Provides
    @Singleton
    internal fun provideEventRepository(demeterDataSourceFactory: DemeterDataSourceFactory,
                                        demeterMapper: DemeterMapper,
                                        actuatorMapper: ActuatorMapper): DemeterRepository {
        return DemeterDataRepository(demeterDataSourceFactory, demeterMapper, actuatorMapper)
    }
}