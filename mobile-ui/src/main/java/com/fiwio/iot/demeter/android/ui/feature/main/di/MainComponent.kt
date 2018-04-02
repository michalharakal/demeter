package com.fiwio.iot.demeter.android.ui.feature.main.di

import com.fiwio.iot.demeter.android.ui.feature.manual.di.ActuatorsListComponent
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun actuatorsListComponent(): ActuatorsListComponent

}