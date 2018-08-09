package com.fiwio.iot.demeter.presentation.mapper

interface ScheduleStringsProvider {
    fun getBranchUserName(internalName:String):String
    fun getCommnadUserName(internalName:String):String
}