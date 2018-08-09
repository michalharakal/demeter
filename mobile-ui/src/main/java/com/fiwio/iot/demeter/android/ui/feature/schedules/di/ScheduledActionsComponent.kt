package com.fiwio.iot.demeter.android.ui.feature.schedules.di

import com.fiwio.iot.demeter.android.ui.feature.manual.ScheduledActionsView
import dagger.Subcomponent


@Subcomponent(modules = arrayOf(ScheduledActionsModule::class))
interface ScheduledActionsComponent {
    fun inject(manualControlView: ScheduledActionsView)
}
