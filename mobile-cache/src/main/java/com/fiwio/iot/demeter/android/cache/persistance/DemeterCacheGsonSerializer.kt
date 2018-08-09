package com.fiwio.iot.demeter.android.cache.persistance

import com.fiwio.iot.demeter.data.model.DemeterEntity
import com.fiwio.iot.demeter.data.model.ScheduledActionsEntity
import com.google.gson.Gson
import java.io.*

class DemeterCacheGsonSerializer(private val baseFolder: String, val gson: Gson) : DemeterCacheSerializer {
    override fun readScheduledActions(): ScheduledActionsEntity {
        synchronized(lock) {
            val sd = File(getSchedulesFullName())
            if (sd.exists()) {
                val inputStream = FileInputStream(sd)
                val reader = InputStreamReader(inputStream)
                return gson.fromJson(reader, ScheduledActionsEntity::class.java)
            } else {
                return ScheduledActionsEntity(emptyList())
            }
        }
    }

    override fun writeScheduledActions(actions: ScheduledActionsEntity) {
        writeData(getDemeterFullName(), actions)
    }

    private fun getDemeterFullName() = baseFolder + "/demeter.json"
    private fun getSchedulesFullName() = baseFolder + "/schedules.json"

    private val lock: Any = Any()

    override fun readDemeter(): DemeterEntity {

        synchronized(lock) {
            val sd = File(getDemeterFullName())
            if (sd.exists()) {
                val inputStream = FileInputStream(sd)
                val reader = InputStreamReader(inputStream)
                return gson.fromJson(reader, DemeterEntity::class.java)
            } else {
                return createDefaultSchedule()
            }
        }
    }

    private fun createDefaultSchedule(): DemeterEntity {
        val sample = """
            {
    "sensors": [
        {
            "name": "INP0",
            "isOn": "ON"
        }
    ],
    "actuators": [
        {
            "name": "BCM23",
            "isOn": "ON"
        },
        {
            "name": "BCM24",
            "isOn": "OFF"
        },
        {
            "name": "BCM25",
            "isOn": "OFF"
        },
        {
            "name": "BCM26",
            "isOn": "OFF"
        },
        {
            "name": "BCM27",
            "isOn": "OFF"
        }
    ]
}
            """
        return gson.fromJson(sample, DemeterEntity::class.java)
    }


    override fun writeDemeter(demeter: DemeterEntity) {
        writeData(getDemeterFullName(), demeter)
    }


    fun writeData(fileName: String, events: Any) {
        val sd = File(fileName)
        sd.createNewFile()

        val fOut = FileOutputStream(sd)
        val myOutWriter = OutputStreamWriter(fOut)
        val json = gson.toJson(events)
        myOutWriter.write(json)
        myOutWriter.flush()
    }

}

interface DemeterCacheSerializer {
    fun readDemeter(): DemeterEntity
    fun writeDemeter(demeter: DemeterEntity)
    fun readScheduledActions(): ScheduledActionsEntity
    fun writeScheduledActions(actions: ScheduledActionsEntity)
}
