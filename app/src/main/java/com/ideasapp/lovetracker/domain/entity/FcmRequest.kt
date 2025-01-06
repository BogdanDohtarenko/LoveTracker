package com.ideasapp.lovetracker.domain.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FcmRequest(
    @SerializedName("message")
    val message: MessageData
) : Serializable