package com.fiwio.iot.demeter.android.ui.feature.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.fiwio.iot.demeter.android.ui.R
import com.fiwio.iot.demeter.android.ui.ext.getAppComponent
import com.fiwio.iot.demeter.android.ui.feature.main.MainActivity
import com.fiwio.iot.demeter.android.ui.feature.main.MainNavigator
import com.fiwio.iot.demeter.android.ui.feature.splash.di.SplashComponent
import com.fiwio.iot.demeter.android.ui.feature.splash.di.SplashModule
import com.fiwio.iot.demeter.presentation.feature.splash.SplashContract
import com.fiwio.iot.demeter.presentation.model.ActuatorView
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject


class SplashActivity : AppCompatActivity(), SplashContract.View, MainNavigator {


    val TWENTY_SECONDS = 5000

    @Inject
    internal lateinit var presenter: SplashContract.Presenter

    private val handler = Handler()
    private var repeatCount = 0
    private var started = false

    private lateinit var component: SplashComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        component = getAppComponent().plus(SplashModule())
        component.injects(this)
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        presenter.attachView(this)
    }

    /*
    private fun startWatchDog() {
        Thread(Runnable {
            try {
                Thread.sleep(TWENTY_SECONDS.toLong())
                handler.post(Runnable { onServiceSearchFailed() })
            } catch (e: Exception) {
            }
        }).start()
    }
    */


    /*
    override fun onServiceFound(ipAddress: String) {
        multicastDns.stopDiscovery()
        val mainIntent = Intent(this, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putString("host", ipAddress)
        mainIntent.putExtras(bundle)
        this.startActivity(mainIntent)
        this.finish()
    }

    override fun onServiceSearchFailed() {
        if (repeatCount >= 3) {
            multicastDns.stopDiscovery()
            if (!started) {
                started = true
                val mainIntent = Intent(this, MainActivity::class.java)
                this.startActivity(mainIntent)
                this.finish()
            }
        } else {
            repeatCount++
            if (multicastDns != null) {
                multicastDns.stopDiscovery()
                multicastDns.discoverServices(this, handler)
                startWatchDog()
            }

        }
    }
    */

    override fun enterUrlByHand() {
        splash_ip_container.visibility = View.VISIBLE
    }

    override fun showLoading(pullToRefresh: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showContent() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(e: Throwable, pullToRefresh: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setData(data: List<ActuatorView>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadData(pullToRefresh: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToMainWithUrl(url: String) {
        MainActivity.navigate(this, url)
    }

    override fun getSystemService(name: String?): Any {
        when (name) {
            "component" -> return component
            else -> return super.getSystemService(name)
        }
    }
}
