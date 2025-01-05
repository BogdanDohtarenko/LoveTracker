package com.ideasapp.lovetracker.data

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ideasapp.lovetracker.R
import com.ideasapp.lovetracker.presentation.activity.MainActivity

class MessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("FCM", "From: ${remoteMessage.from}")

        remoteMessage.notification?.let {
            Log.d("FCM", "Message Notification Title: ${it.title}")
            Log.d("FCM", "Message Notification Body: ${it.body}")

            it.title?.let { title ->
                it.body?.let { body ->
                    showNotification(title, body)
                }
            }
        }

        if (remoteMessage.data.isNotEmpty()) {
            Log.d("FCM", "Message Data Payload: ${remoteMessage.data}")
        }
    }

    private fun showNotification(title: String, body: String) {
        val channelId = "love_tracker_channel"
        val notificationId = System.currentTimeMillis().toInt()

        // Create an intent to open the app when the notification is clicked
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Create a notification channel (required for Android 8.0 and higher)
        val channel = NotificationChannel(
            channelId,
            "Love Tracker Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Channel for Love Tracker notifications"
        }
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification) // Replace with your app's notification icon
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("FCM", "Notification permission not granted.")
            return
        }

        NotificationManagerCompat.from(this).notify(notificationId, notification)
        Log.d("FCM", "Notification displayed with title: $title and body: $body")
    }
}