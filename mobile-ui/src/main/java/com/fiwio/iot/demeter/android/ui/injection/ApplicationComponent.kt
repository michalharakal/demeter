package com.fiwio.iot.demeter.android.ui.injection

import android.app.Application
import com.fiwio.iot.demeter.android.ui.app.DemeterApplication
import com.fiwio.iot.demeter.android.ui.feature.firebase.di.FirebaseComponent
import com.fiwio.iot.demeter.android.ui.feature.firebase.di.FirebaseModule
import com.fiwio.iot.demeter.android.ui.feature.main.di.MainComponent
import com.fiwio.iot.demeter.android.ui.feature.main.di.MainModule
import com.fiwio.iot.demeter.android.ui.feature.main.di.OfflineModule
import com.fiwio.iot.demeter.android.ui.feature.offline.di.OfflineComponent
import com.fiwio.iot.demeter.android.ui.feature.splash.di.SplashComponent
import com.fiwio.iot.demeter.android.ui.feature.splash.di.SplashModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, DataModule::class))
interface ApplicationComponent {

    fun inject(demeterApplication: DemeterApplication)

    fun plus(mainModule: MainModule): MainComponent
    fun plus(offlineModule: OfflineModule): OfflineComponent
    fun plus(splashModule: SplashModule): SplashComponent
    fun plus(firebaseModule: FirebaseModule): FirebaseComponent


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}
