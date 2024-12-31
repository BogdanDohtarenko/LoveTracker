package com.ideasapp.lovetracker.domain.repository

import com.ideasapp.lovetracker.presentation.activity.FcmRequest
import com.ideasapp.lovetracker.presentation.activity.FcmResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FcmService {
    @Headers(
        "Authorization: key= BKKrRaQgS2eHO_KbbufVyoVFyXTYQFvplTbfdAA19-O7iVRB-vrELUmtkxX78Et4u5Zc6v_kNdQidCGb3J08apc" +
                "Content-Type:application/json"
    )
    @POST("fcm/send")
    fun sendNotification(@Body body: FcmRequest): Call<FcmResponse>
}