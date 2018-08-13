package com.fiwio.iot.demeter.data.model

import org.joda.time.DateTime

data class MessageEntity(val timeStamp: DateTime, val title: String, val message: String)