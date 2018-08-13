package com.fiwio.iot.demeter.android.ui.feature.messages.di

import com.fiwio.iot.demeter.domain.features.messages.GetMessages
import com.fiwio.iot.demeter.presentation.feature.message.MessagesContract
import com.fiwio.iot.demeter.presentation.feature.message.MessagesPresenter
import com.fiwio.iot.demeter.presentation.mapper.MessagesMapper
import dagger.Module
import dagger.Provides

@Module
class MessagesModule {

    @Provides
    internal fun provideMessagesPresenter(getMessages: GetMessages,
                                          messagesMapper: MessagesMapper): MessagesContract.Presenter {
        return MessagesPresenter(getMessages, messagesMapper)
    }
}