package com.fiwio.iot.demeter.android.ui.app

import android.app.Activity
import android.app.Application
import android.os.Bundle

class AppLifecycleTracker : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }

    private var numStarted = 0

    override fun onActivityStarted(activity: Activity?) {
        if (numStarted == 0) {


        }
        numStarted++
    }

    override fun onActivityStopped(activity: Activity?) {
        numStarted--
        if (numStarted == 0) {
            //RefreshIntentService.stopRefresh(activity!!.application)
        }
    }

}