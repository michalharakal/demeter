package com.fiwio.iot.demeter.android.ui.injection

import android.app.Application
import com.fiwio.iot.demeter.android.ui.app.DemeterApplication
import com.fiwio.iot.demeter.android.ui.feature.main.di.MainComponent
import com.fiwio.iot.demeter.android.ui.feature.main.di.MainModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, DataModule::class))
interface ApplicationComponent {

    fun inject(demeterApplication: DemeterApplication)
    fun mainComponent(mainModule: MainModule): MainComponent


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}
