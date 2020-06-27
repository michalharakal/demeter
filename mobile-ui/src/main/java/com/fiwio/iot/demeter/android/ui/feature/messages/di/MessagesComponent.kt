package com.fiwio.iot.demeter.android.ui.feature.messages.di

import com.fiwio.iot.demeter.android.ui.feature.messages.MessagesView
import dagger.Subcomponent


@Subcomponent(modules = arrayOf(MessagesModule::class))
interface MessagesComponent {
    fun inject(messagesView: MessagesView)
}
