package com.fiwio.iot.demeter.android.cache.persistance

import com.fiwio.iot.demeter.domain.model.Demeter
import com.google.gson.Gson

class DemeterCacheGsonSerializer(private val baseFolder: String, val gson: Gson) : DemeterCacheSerializer {

    private fun getSpeakersFullName() = baseFolder + "/demeter.json"

    override fun readDemeter(): Demeter {
        val sample = """
            {
    "inputs": [
        {
            "name": "INP0",
            "value": "OFF"
        }
    ],
    "relays": [
        {
            "name": "BCM23",
            "value": "OFF"
        },
        {
            "name": "BCM24",
            "value": "OFF"
        },
        {
            "name": "BCM25",
            "value": "OFF"
        },
        {
            "name": "BCM26",
            "value": "OFF"
        },
        {
            "name": "BCM27",
            "value": "OFF"
        },
        {
            "name": "BCM28",
            "value": "OFF"
        },
        {
            "name": "BCM29",
            "value": "OFF"
        },
        {
            "name": "BCM30",
            "value": "OFF"
        }
    ]
}
            """
        return gson.fromJson(sample, Demeter::class.java)

    }

    override fun writeDemeter(demeter: Demeter) {
    }

}

interface DemeterCacheSerializer {
    fun readDemeter(): Demeter
    fun writeDemeter(demeter: Demeter)
}
