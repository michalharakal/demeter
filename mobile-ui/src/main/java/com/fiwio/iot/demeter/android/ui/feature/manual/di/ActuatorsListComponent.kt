package com.fiwio.iot.demeter.android.ui.feature.manual.di

import com.fiwio.iot.demeter.android.ui.feature.manual.ManualControlView
import dagger.Subcomponent


@Subcomponent(modules = arrayOf(ActuatorsListModule::class))
interface ActuatorsListComponent {
    fun inject(manualControlView: ManualControlView)
}
