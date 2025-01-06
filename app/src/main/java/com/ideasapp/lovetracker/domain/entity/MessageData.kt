package com.ideasapp.lovetracker.domain.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MessageData(
    @SerializedName("topic")
    val topic: String,

    @SerializedName("notification")
    val notification: NotificationData
) : Serializable