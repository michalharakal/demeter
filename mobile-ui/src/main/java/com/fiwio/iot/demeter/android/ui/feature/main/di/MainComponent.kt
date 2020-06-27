package com.fiwio.iot.demeter.android.ui.feature.main.di

import com.fiwio.iot.demeter.android.ui.feature.main.MainActivity
import com.fiwio.iot.demeter.android.ui.feature.manual.di.ActuatorsListComponent
import com.fiwio.iot.demeter.android.ui.feature.messages.di.MessagesComponent
import com.fiwio.iot.demeter.android.ui.feature.schedules.di.ScheduledActionsComponent
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(mainActivity: MainActivity)
    fun actuatorsListComponent(): ActuatorsListComponent
    fun scheduledComponent(): ScheduledActionsComponent
    fun messagesComponent(): MessagesComponent
}