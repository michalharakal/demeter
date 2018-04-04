package com.fiwio.iot.demeter.android.ui.feature.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.fiwio.iot.demeter.android.ui.R
import com.fiwio.iot.demeter.android.ui.app.EndpointUrlProvider
import com.fiwio.iot.demeter.android.ui.ext.getAppComponent
import com.fiwio.iot.demeter.android.ui.feature.main.di.MainComponent
import com.fiwio.iot.demeter.android.ui.feature.main.di.MainModule
import com.fiwio.iot.demeter.android.ui.feature.manual.ManualControlView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), AutomaticNavigator {


    @Inject
    lateinit var endpointUrlProvider: EndpointUrlProvider


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

        component = getAppComponent().mainComponent(MainModule(this))

        endpointUrlProvider.url = intent.getStringExtra("url")
    }

    override fun getSystemService(name: String?): Any {
        when (name) {
            "component" -> return component
            else -> return super.getSystemService(name)
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
