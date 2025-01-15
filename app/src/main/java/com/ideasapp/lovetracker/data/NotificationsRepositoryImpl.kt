package com.ideasapp.lovetracker.data

import android.content.Context
import android.util.Log
import com.ideasapp.lovetracker.domain.entity.FcmRequest
import com.ideasapp.lovetracker.domain.entity.FcmResponse
import com.ideasapp.lovetracker.domain.entity.MessageData
import com.ideasapp.lovetracker.domain.entity.NotificationData
import com.ideasapp.lovetracker.domain.repository.FcmService
import com.ideasapp.lovetracker.domain.repository.NotificationsRepository
import com.ideasapp.lovetracker.presentation.activity.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NotificationsRepositoryImpl: NotificationsRepository {
    override fun createMissYouNotification(context: Context, notificationData: NotificationData) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val authorizationHeader = "Bearer ${getFirebaseToken(context)}"
                val fcmService = setUpRetrofit()
                val fcmRequest = createNotification(notificationData)
                fcmService.sendNotification(authorizationHeader, fcmRequest)
                    .enqueue(object : Callback<FcmResponse> {
                        override fun onResponse(call: Call<FcmResponse>, response: Response<FcmResponse>) {
                            if (response.isSuccessful) {
                                val fcmResponse = response.body()
                                Log.d("FCM", "Notification sent successfully: $fcmResponse")
                            } else {
                                val errorBody = response.errorBody()?.string()
                                Log.d("FCM", "Failed to send notification: HTTP ${response.code()} - ${response.message()}")
                                Log.d("FCM", "Error body: $errorBody")
                            }
                        }
                        override fun onFailure(call: Call<FcmResponse>, t: Throwable) {
                            Log.d("FCM", "Error sending notification: ${t.message}")
                        }
                    })
            } catch (e: Exception) {
                Log.e("FCM", "Error: ${e.message}")
            }
        }
    }

    private suspend fun getFirebaseToken(context: Context): String {
        val token = withContext(Dispatchers.IO) {
            FirebaseTokenManager.getAccessToken(context)
        }
        return token
    }
    private suspend fun setUpRetrofit(): FcmService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
        return retrofit.create(FcmService::class.java)
    }

    private suspend fun createNotification(notificationData: NotificationData): FcmRequest {
        val messageData = MessageData(
            topic = MainActivity.TOPIC,
            notification = notificationData
        )
        return FcmRequest(message = messageData)
    }
}