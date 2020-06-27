package com.fiwio.iot.demeter.android.ui.feature.firebase.di

import com.fiwio.iot.demeter.domain.features.messages.NotificationGateway
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {

    @Provides
    fun provideNotificationGateway(): NotificationGateway {
        return object : NotificationGateway {
            override fun showNotification(params: String) {

            }
        }
    }
}