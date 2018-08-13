package com.fiwio.iot.demeter.presentation.mapper

import com.fiwio.iot.demeter.domain.model.Message
import com.fiwio.iot.demeter.domain.model.Messages
import com.fiwio.iot.demeter.presentation.model.MessageModel
import com.fiwio.iot.demeter.presentation.model.MessagesModel
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject


class MessagesMapper @Inject constructor() {
    fun mapToView(t: Messages): MessagesModel {
        return MessagesModel(t.messages.sortedBy { it.timeStamp }
                .map { message -> mapToMessageModel(message) })
    }

    private fun mapToMessageModel(message: Message): MessageModel {
        return MessageModel(dateTimeToStr(message.timeStamp), message.title, message.message)
    }

    private fun dateTimeToStr(timeStamp: DateTime): String {
        val dt = DateTime()
        val fmt = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss")
        return fmt.print(dt)
    }
}