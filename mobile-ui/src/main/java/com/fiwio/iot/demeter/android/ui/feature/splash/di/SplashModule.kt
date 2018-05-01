package com.fiwio.iot.demeter.android.ui.feature.splash.di

import android.content.Context
import com.fiwio.iot.demeter.android.networking.aspects.discovery.MulticastDns
import com.fiwio.iot.demeter.android.networking.aspects.discovery.NdsDiscovery
import com.fiwio.iot.demeter.android.networking.aspects.discovery.NotSupportedNdsDiscovery
import com.fiwio.iot.demeter.domain.features.splash.FindDemeter
import com.fiwio.iot.demeter.presentation.feature.splash.SplashContract
import com.fiwio.iot.demeter.presentation.feature.splash.SplashPresenter
import com.fiwio.iot.demeter.presentation.mapper.DnsLookupStateMapper
import dagger.Module
import dagger.Provides

@Module
class SplashModule {

    @Provides
    internal fun provideHomePresenter(context: Context): MulticastDns {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            NdsDiscovery(context)
        } else
            NotSupportedNdsDiscovery()
    }

    @Provides
    internal fun provideSplashPresenter(findDemeter: FindDemeter, dnsLookupStateMapper: DnsLookupStateMapper): SplashContract.Presenter {
        return SplashPresenter(findDemeter, dnsLookupStateMapper)
    }
}
