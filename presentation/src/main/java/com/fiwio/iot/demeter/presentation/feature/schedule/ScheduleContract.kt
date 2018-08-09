package com.fiwio.iot.demeter.presentation.feature.schedule

import com.fiwio.iot.demeter.presentation.model.ScheduledActionsModel
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView

interface ScheduleContract {

    interface View : MvpLceView<ScheduledActionsModel>

    interface Presenter : MvpPresenter<View>

}