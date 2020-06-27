package com.fiwio.iot.demeter.data.source

import com.fiwio.iot.demeter.data.model.MessageEntity
import com.fiwio.iot.demeter.data.model.MessagesEntities
import com.fiwio.iot.demeter.data.repository.MessageDataStore
import com.fiwio.iot.demeter.data.repository.MessagesCache
import javax.inject.Inject

class MessagesCacheDataStore @Inject constructor(private val messagesCache: MessagesCache) : MessageDataStore {

    override fun getMessages(): MessagesEntities {
        return messagesCache.getMessages()
    }

    override fun addMessage(message: MessageEntity) {
        return messagesCache.addMessage(message)
    }
}