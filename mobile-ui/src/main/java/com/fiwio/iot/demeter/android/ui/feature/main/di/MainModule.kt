package com.fiwio.iot.demeter.android.ui.feature.main.di

import com.fiwio.iot.demeter.android.ui.feature.main.AutomaticNavigator
import dagger.Module
import dagger.Provides

@Module
class MainModule(val automaticNavigator: AutomaticNavigator) {

    @Provides
    fun automaticNavigator(): AutomaticNavigator {
        return automaticNavigator
    }
}