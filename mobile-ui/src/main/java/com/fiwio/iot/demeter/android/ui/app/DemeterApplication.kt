package com.fiwio.iot.demeter.android.ui.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.fiwio.iot.demeter.android.ui.injection.ApplicationComponent
import com.fiwio.iot.demeter.android.ui.injection.DaggerApplicationComponent
import io.reactivex.internal.functions.Functions
import io.reactivex.plugins.RxJavaPlugins
import android.app.NotificationManager
import android.app.NotificationChannel
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Build.VERSION
import android.os.Build.VERSION.SDK_INT
import com.fiwio.iot.demeter.android.ui.R


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

        RxJavaPlugins.setErrorHandler(Functions.emptyConsumer())

        createNotificationChannel()
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

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val description = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val CHANNEL_ID = "cml"
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
    }
}
