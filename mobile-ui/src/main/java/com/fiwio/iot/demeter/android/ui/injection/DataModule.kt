package com.fiwio.iot.demeter.android.ui.injection

import android.app.Application
import android.content.Context
import com.fiwio.iot.demeter.android.cache.DemeterGsonCache
import com.fiwio.iot.demeter.android.cache.MessageGsonCache
import com.fiwio.iot.demeter.android.cache.PreferencesHelper
import com.fiwio.iot.demeter.android.cache.persistance.DemeterCacheGsonSerializer
import com.fiwio.iot.demeter.android.cache.persistance.MessagesGsonSerializer
import com.fiwio.iot.demeter.android.ui.app.AppResourcesActuatorNameMapper
import com.fiwio.iot.demeter.data.mapper.ActuatorMapper
import com.fiwio.iot.demeter.data.mapper.FsmMapper
import com.fiwio.iot.demeter.data.mapper.MessagesMapper
import com.fiwio.iot.demeter.data.mapper.ScheduledActionMapper
import com.fiwio.iot.demeter.data.repository.*
import com.fiwio.iot.demeter.data.source.DemeterDataSourceFactory
import com.fiwio.iot.demeter.data.source.MessagesCacheDataStore
import com.fiwio.iot.demeter.domain.connectivity.ConnectionSession
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import com.fiwio.iot.demeter.domain.repository.MessagesRepository
import com.fiwio.iot.demeter.presentation.mapper.ActuatorNameMapper
import com.fiwio.iot.demeter.remote.firebase.remote.firebase.mapper.DemeterEntityMapper
import com.fiwio.iot.demeter.remote.firebase.remote.firebase.mapper.FsmEntityMapper
import com.fiwio.iot.demeter.remote.firebase.remote.firebase.mapper.SchedulesEntityMapper
import com.fiwio.iot.demeter.remote.firebase.remote.source.DemeterRemoteImpl
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
    internal fun provideConnectionSession():ConnectionSession {
        return object : ConnectionSession {
            override fun getCmlId(): String {
                return "ID"
            }
        }
    }

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
    internal fun provideEventRemote(session: ConnectionSession, service: DefaultApi, entityMapper: DemeterEntityMapper,
                                    schedulesEntityMapper: SchedulesEntityMapper,
                                    fsmEntityMapper: FsmEntityMapper): DemeterRemote {
        return DemeterRemoteImpl(session, service, entityMapper, schedulesEntityMapper, fsmEntityMapper)
    }

    @Provides
    @Singleton
    internal fun provideEventRepository(demeterDataSourceFactory: DemeterDataSourceFactory,
                                        demeterMapper: DemeterMapper,
                                        schedulesMapper: ScheduledActionMapper,
                                        fsmMapper: FsmMapper,
                                        actuatorMapper: ActuatorMapper): DemeterRepository {
        return DemeterDataRepository(demeterDataSourceFactory, demeterMapper, schedulesMapper,
                fsmMapper, actuatorMapper)
    }

    @Provides
    @Singleton
    internal fun provideMessageCache(application: Application, gson: Gson): MessagesCache {
        val baseCacheFolder = application.filesDir.absolutePath
        return MessageGsonCache(MessagesGsonSerializer(baseCacheFolder, gson))
    }

    @Provides
    @Singleton
    internal fun provideMessagesRepository(messagesCache: MessagesCacheDataStore,
                                          messagesMapper: MessagesMapper): MessagesRepository {
        return MessagesDataRepository(messagesCache, messagesMapper)
    }

}