package com.fiwio.iot.demeter.presentation.feature.manual

import com.fiwio.iot.demeter.presentation.model.ActuatorView
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView

/**
 * Defines a contract of operations between the Events Presenter and Events View
 */
interface ActuatorsListContract {

    interface View : MvpLceView<List<ActuatorView>>

    interface Presenter : MvpPresenter<View>

}