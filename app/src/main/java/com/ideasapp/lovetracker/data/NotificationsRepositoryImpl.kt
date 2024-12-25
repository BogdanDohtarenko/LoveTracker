package com.ideasapp.lovetracker.data

import android.util.Log
import com.ideasapp.lovetracker.domain.repository.NotificationsRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

object NotificationsRepositoryImpl: NotificationsRepository {
    override fun createMissYouNotification() {
        val client = OkHttpClient()
        val json = JSONObject()
        json.put("to", "/topics/global")

        val notification = JSONObject()
        notification.put("title", "Miss you")
        notification.put("body", "Every time")
        json.put("notification", notification)

        val data = JSONObject()
        data.put("key1", "значение1")
        data.put("key2", "значение2")
        json.put("data", data)

        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            json.toString()
        )

        val request = Request.Builder()
            .url("https://fcm.googleapis.com/fcm/send")
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "key=BI0z0-yLOUWbsXEbICOQ2FwZcRhKnuicv1ZEOgerbZ8XAwiG9VWBxT9pw3FKI3aaapJxbjWoCg6X5INpzQMkNNA") // Замените YOUR_SERVER_KEY на ваш Server Key
            .build()

        val call = client.newCall(request)
        call.execute().use { response ->
            if (response.isSuccessful) {
                Log.d("FCM", "Success: ${response.body?.string()}")
            } else {
                Log.d("FCM","Error: ${response.code}, ${response.message}")
            }
        }
    }

}