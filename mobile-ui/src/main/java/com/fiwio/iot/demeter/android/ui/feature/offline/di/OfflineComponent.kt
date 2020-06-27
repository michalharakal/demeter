package com.fiwio.iot.demeter.android.ui.feature.offline.di

import com.fiwio.iot.demeter.android.ui.feature.main.di.OfflineModule
import com.fiwio.iot.demeter.android.ui.feature.messages.di.MessagesComponent
import com.fiwio.iot.demeter.android.ui.feature.offline.OfflineActivity
import com.fiwio.iot.demeter.android.ui.feature.schedules.di.ScheduledActionsComponent
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(OfflineModule::class))
interface OfflineComponent {
    fun inject(offlineActivity: OfflineActivity)
    fun messagesComponent(): MessagesComponent
}