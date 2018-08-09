package com.fiwio.iot.demeter.android.ui.feature.schedules

import com.fiwio.iot.demeter.presentation.mapper.ScheduleStringsProvider

class DemeterScheduleStringsProvider : ScheduleStringsProvider {
    override fun getCommnadUserName(internalName: String): String {
        return internalName
    }

    override fun getBranchUserName(internalName: String): String {
        return internalName
    }
}
