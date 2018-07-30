package com.fiwio.iot.demeter.android.ui.feature.refresh

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Handler
import com.fiwio.iot.demeter.android.ui.ext.getAppComponent
import com.fiwio.iot.demeter.android.ui.feature.refresh.di.RefreshServiceComponent
import com.fiwio.iot.demeter.android.ui.feature.refresh.di.RefreshServiceModule
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import javax.inject.Inject
import android.os.HandlerThread
import mu.KotlinLogging


private const val ACTION_START_REFRESH = "com.fiwio.iot.demeter.android.ui.feature.refresh.action.START_REFRESH"
private const val ACTION_STOP_REFRESH = "com.fiwio.iot.demeter.android.ui.feature.refresh.action.STOP_REFRESH"

private val logger = KotlinLogging.logger {}

class RefreshIntentService : IntentService("RefreshIntentService") {

    lateinit var handler :Handler

    lateinit var component: RefreshServiceComponent

    @Inject
    lateinit var demeterRepository: DemeterRepository

    private val runnableCode = object : Runnable {
        override fun run() {

            logger.debug { "Refreshing demeter status" }

            demeterRepository.refresh()
            // Repeat this the same runnable code block again another 2 seconds
            // 'this' is referencing the Runnable object
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate() {
        super.onCreate()

        startTread()

        component = getAppComponent().plus(RefreshServiceModule())
        component.inject(this)
    }

    private fun startTread() {
        val thread = HandlerThread("RefreshHandlerThread")
        thread.start()
        handler = Handler(thread.looper)
    }

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_START_REFRESH -> {
                handleActionStartRefresh()
            }
            ACTION_STOP_REFRESH -> {
                handleActionStopRefresh()
            }
        }
    }

    override fun getSystemService(name: String?): Any {
        return when (name) {
            "component" -> component
            else -> super.getSystemService(name)
        }
    }

    private fun handleActionStartRefresh() {
        handler.post(runnableCode)
    }

    private fun handleActionStopRefresh() {
        handler.removeCallbacks(runnableCode)
    }

    companion object {

        @JvmStatic
        fun startRefresh(context: Context) {
            val intent = Intent(context, RefreshIntentService::class.java).apply {
                action = ACTION_START_REFRESH
            }
            context.startService(intent)
        }

        @JvmStatic
        fun stopRefresh(context: Context) {
            val intent = Intent(context, RefreshIntentService::class.java).apply {
                action = ACTION_STOP_REFRESH
            }
            context.startService(intent)
        }
    }
}
