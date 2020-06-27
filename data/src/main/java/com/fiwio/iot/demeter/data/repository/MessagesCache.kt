package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.model.MessageEntity
import com.fiwio.iot.demeter.data.model.MessagesEntities

interface MessagesCache {
    fun getMessages(): MessagesEntities
    fun addMessage(message: MessageEntity)
}