package com.fiwio.iot.demeter.presentation.feature.message

import com.fiwio.iot.demeter.presentation.model.MessagesModel
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView

interface MessagesContract {

    interface View : MvpLceView<MessagesModel>

    interface Presenter : MvpPresenter<View>

}