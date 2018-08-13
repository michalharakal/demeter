package com.fiwio.iot.demeter.domain.model

data class DayTime(val hour: Int, val minute: Int, val second: Int) : Comparable<DayTime> {
    override fun compareTo(other: DayTime): Int {
        return (hour * 3600 + minute * 60 + second) - (other.hour * 3600 + other.minute * 60 + other.second)
    }
}