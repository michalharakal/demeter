package com.fiwio.iot.demeter.android.ui.injection

import android.app.Application
import android.content.Context
import android.util.Log
import com.fatboyindustrial.gsonjodatime.Converters
import com.fiwio.iot.demeter.android.networking.aspects.connectivity.AndroidConnectivityState
import com.fiwio.iot.demeter.android.networking.datasource.DiscoveryDemeterFinder
import com.fiwio.iot.demeter.android.ui.app.EndpointUrlProvider
import com.fiwio.iot.demeter.android.ui.app.StringEndpointUrlProvider
import com.fiwio.iot.demeter.android.ui.app.UiThread
import com.fiwio.iot.demeter.android.ui.feature.schedules.DemeterScheduleStringsProvider
import com.fiwio.iot.demeter.data.executor.JobExecutor
import com.fiwio.iot.demeter.domain.connectivity.ConnectivityState
import com.fiwio.iot.demeter.domain.executor.PostExecutionThread
import com.fiwio.iot.demeter.domain.executor.ThreadExecutor
import com.fiwio.iot.demeter.domain.features.schedule.TimeProvider
import com.fiwio.iot.demeter.domain.model.DayTime
import com.fiwio.iot.demeter.domain.repository.DemeterFinder
import com.fiwio.iot.demeter.presentation.mapper.ScheduleStringsProvider
import com.fiwo.iot.demeter.api.DefaultApi
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import mu.KotlinLogging
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.LocalTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private val logger = KotlinLogging.logger {}

/**
 * Module used to provide dependencies at an application-level.
 */
@Module
open class ApplicationModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Provides
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }

    @Singleton
    @Provides
    fun provideConnectivityState(): ConnectivityState {
        return AndroidConnectivityState()
    }

    @Singleton
    @Provides
    fun provideEndpointUrlProvider(): EndpointUrlProvider {
        return StringEndpointUrlProvider("https://demeter.fiw.io/rest/")
    }

    @Singleton
    @Provides
    fun provideDemeterFinder(context: Context): DemeterFinder {
        return DiscoveryDemeterFinder(context)
    }

    @Singleton
    @Provides
    fun providOkHttpClient(application: Application): OkHttpClient {

        val cacheSize = 10 * 1024 * 1024L // 10 MB
        val cache = Cache(application.getCacheDir(), cacheSize)

        val interceptor = HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger { it ->
                    Log.d("ApplicationModule", it)
                    logger.info { it }
                })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cache(cache)
                .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return Converters.registerDateTime(gsonBuilder).create()
    }


    @Provides
    @Singleton
    internal fun provideConfernceService(client: OkHttpClient, gson: Gson, endpointUrlProvider: EndpointUrlProvider): DefaultApi {
        var restAdapter = Retrofit.Builder()
                .baseUrl(endpointUrlProvider.url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return restAdapter.create(DefaultApi::class.java)
    }

    @Provides
    @Singleton
    fun provideScheduleStringsProvider(): ScheduleStringsProvider {
        return DemeterScheduleStringsProvider()

    }

    @Provides
    @Singleton
    fun provideTimeProvider(): TimeProvider {
        return object : TimeProvider {
            override fun getCurrentTime(): DayTime {
                val date = LocalTime()
                return DayTime(date.hourOfDay, date.minuteOfHour, date.secondOfMinute)
            }
        }
    }
}


