package com.ideasapp.lovetracker.domain.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class NotificationData(
    @SerializedName("title")
    val title: String,

    @SerializedName("body")
    val body: String
) : Serializable
