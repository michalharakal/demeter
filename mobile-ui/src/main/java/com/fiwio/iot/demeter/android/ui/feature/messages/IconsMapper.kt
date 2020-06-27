package com.fiwio.iot.demeter.android.ui.feature.messages

import com.fiwio.iot.demeter.android.ui.R
import java.util.*
import javax.inject.Inject

class IconsMapper @Inject constructor() {
    fun getIconFromName(name: String): Int {

        return when (processName(name.toLowerCase(Locale.getDefault()))) {
            "flowers" -> R.drawable.ic_flower
            "garden" -> R.drawable.ic_garden
            "greenhouse" -> R.drawable.ic_greenhouse
            "filling" -> R.drawable.ic_water
            "swimmer" -> R.drawable.ic_barrel
            else -> R.drawable.ic_message
        }
    }

    /**
    "BCM23" -> context.getString(R.string.filling)
    "BCM24" -> context.getString(R.string.garden)
    "BCM25" -> context.getString(R.string.flowers)
    "BCM26" -> context.getString(R.string.greenhouse)
    "BCM27" -> context.getString(R.string.swimmer)
     */
    private fun processName(name: String): String {
        if (name.contains("flowers") || (name.contains("bcm25"))) {
            return "flowers"
        }
        if (name.contains("greenhouse") || (name.contains("bcm26"))) {
            return "greenhouse"
        }
        if (name.contains("garden") || (name.contains("bcm24"))) {
            return "garden"
        }

        if (name.contains("fill") || (name.contains("bcm23"))) {
            return "filling"
        }

        if (name.contains("swim") || (name.contains("bcm27"))) {
            return "swimmer"
        }


        return ""
    }
}