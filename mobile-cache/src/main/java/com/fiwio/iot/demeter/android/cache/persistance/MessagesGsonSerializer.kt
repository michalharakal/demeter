package com.fiwio.iot.demeter.android.cache.persistance

import com.fiwio.iot.demeter.data.model.MessagesEntities
import com.google.gson.Gson
import java.io.*

class MessagesGsonSerializer(private val baseFolder: String, val gson: Gson) : MessagesGsonCacheSerializer {
    private val lock: Any = Any()
    private fun getMessagesFullName() = baseFolder + "/messages.json"


    override fun readMessages(): MessagesEntities {
        synchronized(lock) {
            val sd = File(getMessagesFullName())
            if (sd.exists()) {
                val inputStream = FileInputStream(sd)
                val reader = InputStreamReader(inputStream!!)
                return gson.fromJson(reader, MessagesEntities::class.java)
            } else {
                return MessagesEntities(emptyList())
            }
        }
    }

    override fun writeMessages(messages: MessagesEntities) {
        synchronized(lock) {
            writeData(getMessagesFullName(), messages)
        }
    }

    private fun writeData(fileName: String, events: Any) {
        val sd = File(fileName)
        sd.createNewFile()

        val fOut = FileOutputStream(sd)
        val myOutWriter = OutputStreamWriter(fOut)
        val json = gson.toJson(events)
        myOutWriter.write(json)
        myOutWriter.flush()
    }


}

interface MessagesGsonCacheSerializer {
    fun readMessages(): MessagesEntities
    fun writeMessages(messages: MessagesEntities)
}