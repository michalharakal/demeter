package com.fiwio.iot.demeter.android.ui.feature.splash

import android.os.Build
import android.os.Bundle
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
import com.fiwio.iot.demeter.presentation.model.DnsLookupState
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject


class SplashActivity : AppCompatActivity(), SplashContract.View, MainNavigator {

    override fun showContent() {
        loading_container.visibility = View.VISIBLE
    }

    override fun setData(data: DnsLookupState) {
        if (data.found) {
            navigateToMainWithUrl(data.ipAdress)
        }
    }


    @Inject
    internal lateinit var presenter: SplashContract.Presenter

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

        manual_ip_button.setOnClickListener {
            val text = manual_ip_edit.editableText.toString()
            navigateToMainWithUrl(text)
        }

        start.setOnClickListener {
            val text = manual_ip_edit.editableText.toString()
            navigateToMainWithUrl("http://192.168.3.54:8080")
        }
    }

    override fun enterUrlByHand() {
        loading_container.visibility = View.GONE
        manual_ip_container.visibility = View.VISIBLE
    }

    override fun showLoading(pullToRefresh: Boolean) {
        loading_container.visibility = View.VISIBLE
        manual_ip_container.visibility = View.GONE
    }

    override fun showError(e: Throwable, pullToRefresh: Boolean) {
    }

    override fun loadData(pullToRefresh: Boolean) {
    }

    override fun navigateToMainWithUrl(url: String) {
        MainActivity.navigate(this, url)
    }

    override fun getSystemService(name: String?): Any {
        return when (name) {
            "component" -> component
            else -> super.getSystemService(name)
        }
    }
}
