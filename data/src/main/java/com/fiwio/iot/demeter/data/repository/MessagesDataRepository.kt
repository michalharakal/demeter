package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.mapper.MessagesMapper
import com.fiwio.iot.demeter.data.source.MessagesCacheDataStore
import com.fiwio.iot.demeter.domain.model.Message
import com.fiwio.iot.demeter.domain.model.Messages
import com.fiwio.iot.demeter.domain.repository.MessagesRepository
import io.reactivex.Observable

class MessagesDataRepository(val messagesCache: MessagesCacheDataStore,
                             val messagesMapper: MessagesMapper) : MessagesRepository {
    override fun add(message: Message) {
        messagesCache.addMessage(messagesMapper.mapMessageToEntity(message))
    }

    override fun getMessages(): Observable<Messages> {
        return observableInstance()
    }

    private lateinit var messagesObservable: Observable<Messages>
    private val lock = Any()

    private fun observableInstance(): Observable<Messages> {
        synchronized(lock) {
            if (messagesObservable == null) {
                messagesObservable = Observable.create {
                    messagesMapper.mapFromEntity(messagesCache.getMessages())
                }
            }
            return messagesObservable
        }
    }
}