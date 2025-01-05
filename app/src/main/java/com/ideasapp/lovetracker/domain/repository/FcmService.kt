package com.ideasapp.lovetracker.domain.repository

import com.ideasapp.lovetracker.presentation.activity.FcmRequest
import com.ideasapp.lovetracker.presentation.activity.FcmResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface FcmService {
    @POST("projects/lovetracker-ac855/messages:send")
    fun sendNotification(
        @Header("Authorization") authorization: String,
        @Body fcmRequest: FcmRequest
    ): Call<FcmResponse>
}