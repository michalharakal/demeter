package com.fiwio.iot.demeter.android.ui.feature.refresh.di

import com.fiwio.iot.demeter.android.ui.feature.refresh.RefreshIntentService
import dagger.Subcomponent


@Subcomponent(modules = arrayOf(RefreshServiceModule::class))
interface RefreshServiceComponent {
    fun inject(refreshIntentService: RefreshIntentService)
}

