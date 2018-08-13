package com.fiwio.iot.demeter.domain.repository

import com.fiwio.iot.demeter.domain.model.Message
import com.fiwio.iot.demeter.domain.model.Messages
import io.reactivex.Observable

interface MessagesRepository {
    fun getMessages(): Observable<Messages>
    fun add(message: Message)
}