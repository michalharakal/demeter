package com.fiwio.iot.demeter.android.cache.persistance

import com.fiwio.iot.demeter.data.model.DemeterEntity
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class DemeterCacheGsonSerializer(private val baseFolder: String, val gson: Gson) : DemeterCacheSerializer {

    private fun getActuatorsFullName() = baseFolder + "/actuators.json"
    private fun getSensorsFullName() = baseFolder + "/sensors.json"


    override fun readDemeter(): DemeterEntity {
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
        },
        {
            "name": "BCM28",
            "isOn": "OFF"
        },
        {
            "name": "BCM29",
            "isOn": "OFF"
        },
        {
            "name": "BCM30",
            "isOn": "OFF"
        }
    ]
}
            """
        return gson.fromJson(sample, DemeterEntity::class.java)

    }

    override fun writeDemeter(demeter: DemeterEntity) {
        writeList(getActuatorsFullName(), demeter.actuators)
        writeList(getSensorsFullName(), demeter.sensors)
    }

    fun writeList(fileName: String, events: List<Any>) {
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
}
