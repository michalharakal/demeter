package com.fiwio.iot.demeter.android.ui.feature.offline.di

import com.fiwio.iot.demeter.android.ui.feature.main.di.OfflineModule
import com.fiwio.iot.demeter.android.ui.feature.offline.OfflineActivity
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(OfflineModule::class))
interface OfflineComponent {
    fun inject(offlineActivity: OfflineActivity)
}