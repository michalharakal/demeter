package com.fiwio.iot.demeter.android.ui.feature.main.di

import com.fiwio.iot.demeter.android.ui.feature.offline.OfflineNavigator
import dagger.Module
import dagger.Provides

@Module
class OfflineModule(val offlineNavigator: OfflineNavigator) {

    @Provides
    fun provideOfflineNavigator(): OfflineNavigator {
        return offlineNavigator
    }
}