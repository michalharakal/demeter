package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.model.DemeterEntity

interface DemeterFirebase {
    fun getDemeterImage(id: String, onResult: (DemeterEntity) -> Unit)
}