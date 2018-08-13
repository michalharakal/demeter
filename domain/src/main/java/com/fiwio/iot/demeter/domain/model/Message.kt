package com.fiwio.iot.demeter.domain.model

import org.joda.time.DateTime

data class Message(val timeStamp: DateTime, val title: String, val message: String)