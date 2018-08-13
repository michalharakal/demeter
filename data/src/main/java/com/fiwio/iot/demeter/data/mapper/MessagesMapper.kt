package com.fiwio.iot.demeter.data.mapper

import com.fiwio.iot.demeter.data.model.MessageEntity
import com.fiwio.iot.demeter.data.model.MessagesEntities
import com.fiwio.iot.demeter.domain.model.Message
import com.fiwio.iot.demeter.domain.model.Messages
import org.dukecon.data.mapper.Mapper
import javax.inject.Inject


class MessagesMapper @Inject constructor() : Mapper<MessagesEntities, Messages> {
    override fun mapFromEntity(type: MessagesEntities): Messages {
        return Messages(type.messages.map { it -> mapMessageFromEntity(it) })
    }

    private fun mapMessageFromEntity(it: MessageEntity): Message {
        return Message(it.timeStamp, it.title, it.message)
    }

    override fun mapToEntity(type: Messages): MessagesEntities {
        return MessagesEntities(type.messages.map { it -> mapMessageToEntity(it) })
    }

    fun mapMessageToEntity(it: Message): MessageEntity {
        return MessageEntity(it.timeStamp, it.title, it.message)
    }


}