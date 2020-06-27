package com.fiwio.iot.demeter.android.ui.feature.firebase.di

import com.fiwio.iot.demeter.android.ui.feature.firebase.DemeterFirebaseMessagingService
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(FirebaseModule::class))
interface FirebaseComponent {
    fun inject(demeterFirebaseMessagingService: DemeterFirebaseMessagingService)
}

