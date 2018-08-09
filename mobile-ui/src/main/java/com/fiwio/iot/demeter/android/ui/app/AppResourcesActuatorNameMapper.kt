package com.fiwio.iot.demeter.android.ui.app

import android.content.Context
import com.fiwio.iot.demeter.android.ui.R
import com.fiwio.iot.demeter.presentation.mapper.ActuatorNameMapper

class AppResourcesActuatorNameMapper(val context: Context) : ActuatorNameMapper {
    override fun getUserNameFor(name: String): String {
        return when (name) {
            "BCM23" -> context.getString(R.string.filling)
            "BCM24" -> context.getString(R.string.garden)
            "BCM25" -> context.getString(R.string.flowers)
            "BCM26" -> context.getString(R.string.greenhouse)
            "BCM27" -> context.getString(R.string.swimmer)
            else -> name
        }
    }
}
