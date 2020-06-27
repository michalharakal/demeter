package com.fiwio.iot.demeter.android.cache

import com.fiwio.iot.demeter.android.cache.persistance.MessagesGsonSerializer
import com.fiwio.iot.demeter.data.model.MessageEntity
import com.fiwio.iot.demeter.data.model.MessagesEntities
import com.fiwio.iot.demeter.data.repository.MessagesCache
import javax.inject.Inject

class MessageGsonCache @Inject constructor(val messagesGsonSerializer: MessagesGsonSerializer) : MessagesCache {
    override fun getMessages(): MessagesEntities {
        return messagesGsonSerializer.readMessages()
    }

    override fun addMessage(message: MessageEntity) {
        messagesGsonSerializer.writeMessages(MessagesEntities(getMessages().messages.plus(message)))
    }
}

