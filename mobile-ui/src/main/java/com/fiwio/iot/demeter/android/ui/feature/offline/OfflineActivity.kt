package com.fiwio.iot.demeter.android.ui.feature.offline

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.fiwio.iot.demeter.android.ui.R
import com.fiwio.iot.demeter.android.ui.app.EndpointUrlProvider
import com.fiwio.iot.demeter.android.ui.ext.getAppComponent
import com.fiwio.iot.demeter.android.ui.feature.main.AutomaticNavigator
import com.fiwio.iot.demeter.android.ui.feature.main.MainActivity
import com.fiwio.iot.demeter.android.ui.feature.main.di.OfflineModule
import com.fiwio.iot.demeter.android.ui.feature.manual.ManualControlView
import com.fiwio.iot.demeter.android.ui.feature.manual.ScheduledActionsView
import com.fiwio.iot.demeter.android.ui.feature.offline.di.OfflineComponent
import com.fiwio.iot.demeter.android.ui.feature.refresh.RefreshIntentService
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class OfflineActivity : AppCompatActivity(), AutomaticNavigator, OfflineNavigator {
    override fun showMessages() {

    }


    @Inject
    internal lateinit var endpointUrlProvider: EndpointUrlProvider


    override fun showAutomatic() {
        showView(R.id.navigation_automatic)
    }

    lateinit var component: OfflineComponent

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
        setContentView(R.layout.offline_activity)

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        component = getAppComponent().plus(OfflineModule(this))
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
                        Toast.makeText(this@OfflineActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                })

    }

    override fun onResume() {
        super.onResume()
        RefreshIntentService.startRefresh(this)
    }

    override fun onPause() {
        super.onPause()
        RefreshIntentService.stopRefresh(this)
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
