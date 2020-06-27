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
        return MessagesModel(t.messages.sortedByDescending { it.timeStamp }.
                filter { message -> message.title.toUpperCase().contains("B8:27:EB:7A:D1:BC") }
                .map { message -> mapToMessageModel(message) })
    }

    private fun mapToMessageModel(message: Message): MessageModel {
        return MessageModel(dateTimeToStr(message.timeStamp), message.title, message.message, parseNameFromMessage(message.title))
    }

    private fun parseNameFromMessage(title: String): String {
        return title
    }

    private fun dateTimeToStr(timeStamp: DateTime): String {
        val fmt = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss")
        return fmt.print(timeStamp)
    }
}