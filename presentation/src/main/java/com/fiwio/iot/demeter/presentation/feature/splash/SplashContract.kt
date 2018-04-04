package com.fiwio.iot.demeter.presentation.feature.splash

import com.fiwio.iot.demeter.presentation.model.ActuatorView
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView

/**
 * Defines a contract of operations between the Events Presenter and Events View
 */
interface SplashContract {

    interface View : MvpLceView<List<ActuatorView>> {
        fun enterUrlByHand()
    }

    interface Presenter : MvpPresenter<View> {
        fun startWithUrl(ip:String)
    }

}