package com.fiwio.iot.demeter.android.ui.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.fiwio.iot.demeter.android.ui.injection.ApplicationComponent
import com.fiwio.iot.demeter.android.ui.injection.DaggerApplicationComponent


class DemeterApplication : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent
                .builder()
                .application(this)
                .build()

        component.inject(this)

        registerActivityLifecycleCallbacks(AppLifecycleTracker())
    }




    override fun getSystemService(name: String?): Any {
        return when (name) {
            "component" -> component
            else -> super.getSystemService(name)
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
