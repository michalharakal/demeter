package com.fiwio.iot.demeter.android.ui.feature.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.support.annotation.NonNull
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.fiwio.iot.demeter.android.ui.R
import com.fiwio.iot.demeter.android.ui.app.EndpointUrlProvider
import com.fiwio.iot.demeter.android.ui.ext.getAppComponent
import com.fiwio.iot.demeter.android.ui.feature.main.di.MainComponent
import com.fiwio.iot.demeter.android.ui.feature.main.di.MainModule
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.widget.Toast
import com.fiwio.iot.demeter.android.ui.feature.manual.ManualControlView
import com.fiwio.iot.demeter.android.ui.feature.manual.ScheduledActionsView
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class MainActivity : AppCompatActivity(), AutomaticNavigator {


    @Inject
    internal lateinit var endpointUrlProvider: EndpointUrlProvider

    @Inject
    lateinit var demeterRepository: DemeterRepository

    lateinit var handler: Handler

    private val runnableCode = object : Runnable {
        override fun run() {

            logger.debug { "Refreshing demeter status" }

            try {
                demeterRepository.refresh()
            } catch (e:Exception) {

            }
            // Repeat this the same runnable code block again another 2 seconds
            // 'this' is referencing the Runnable object
            handler.postDelayed(this, 1000)
        }
    }


    override fun showAutomatic() {
        showView(R.id.navigation_automatic)
    }

    lateinit var component: MainComponent

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        showView(item.itemId)
    }

    private fun showView(viewId: Int): Boolean {
        val view: View? = when (viewId) {
            R.id.navigation_manual -> ManualControlView(this)
            R.id.navigation_automatic -> ScheduledActionsView(this)
            else -> null
        }
        if (view != null) {
            content.removeAllViews()
            content.addView(view)
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        component = getAppComponent().plus(MainModule(this))
        component.inject(this)

        endpointUrlProvider.url = intent.getStringExtra("url")

        showView(R.id.navigation_manual)

        FirebaseMessaging.getInstance().subscribeToTopic("events")
                .addOnCompleteListener(object : OnCompleteListener<Void> {
                    override fun onComplete(@NonNull task: Task<Void>) {
                        var msg = getString(R.string.msg_subscribed)
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed)
                        }
                        Log.d("FIRE", msg)
                        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                })



        val handlerThread = HandlerThread("MyHandlerThread")
        handlerThread.start()
        handler = Handler(handlerThread.looper)
        handler.post(runnableCode)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnableCode)
    }

    override fun getSystemService(name: String?): Any {
        return when (name) {
            "component" -> component
            else -> super.getSystemService(name)
        }
    }

    companion object {
        @JvmStatic
        fun navigate(activity: Activity, url: String) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("url", url)

            activity.startActivity(intent)
        }
    }

}
