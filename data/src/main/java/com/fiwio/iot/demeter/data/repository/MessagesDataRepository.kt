package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.mapper.MessagesMapper
import com.fiwio.iot.demeter.data.repository.MessagesDataRepository.MessagesObervable.messagesObservable
import com.fiwio.iot.demeter.data.source.MessagesCacheDataStore
import com.fiwio.iot.demeter.domain.model.Message
import com.fiwio.iot.demeter.domain.model.Messages
import com.fiwio.iot.demeter.domain.repository.MessagesRepository
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class MessagesDataRepository(private val messagesCache: MessagesCacheDataStore,
                             private val messagesMapper: MessagesMapper) : MessagesRepository {

    // kotlin singelton https://github.com/dbacinski/Design-Patterns-In-Kotlin/blob/master/src/main/kotlin/Singleton.kt
    object MessagesObervable {
        val messagesObservable: BehaviorSubject<Messages> = BehaviorSubject.create<Messages>()
    }

    override fun add(message: Message) {
        messagesCache.addMessage(messagesMapper.mapMessageToEntity(message))
        messagesObservable.onNext(messagesMapper.mapFromEntity(messagesCache.getMessages()))
    }

    override fun getMessages(): Observable<Messages> {
        messagesObservable.onNext(messagesMapper.mapFromEntity(messagesCache.getMessages()))
        return messagesObservable
    }
}