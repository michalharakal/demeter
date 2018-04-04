package com.fiwio.iot.demeter.android.networking.aspects.connectivity

import com.fiwio.iot.demeter.domain.connectivity.ConnectivityState


class AndroidConnectivityState(override val isOffline: Boolean = false) : ConnectivityState