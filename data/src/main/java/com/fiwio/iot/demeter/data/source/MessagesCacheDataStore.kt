package com.fiwio.iot.demeter.data.source

import com.fiwio.iot.demeter.data.model.MessageEntity
import com.fiwio.iot.demeter.data.model.MessagesEntities

class MessagesCacheDataStore {

    private var messages: MessagesEntities = MessagesEntities(emptyList())

    fun getMessages(): MessagesEntities {
        return messages
    }

    fun addMessage(message: MessageEntity) {
        messages = MessagesEntities(messages.messages.plus(message))
    }
}