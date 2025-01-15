package com.ideasapp.lovetracker.presentation.models

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.messaging.FirebaseMessaging
import com.ideasapp.lovetracker.data.NotificationsRepositoryImpl
import com.ideasapp.lovetracker.domain.entity.NotificationData
import com.ideasapp.lovetracker.domain.useCases.CreateMissYouNotification
import com.ideasapp.lovetracker.presentation.activity.MainActivity.Companion.TOPIC
import com.ideasapp.lovetracker.presentation.elements.NotificationDialog

class MainViewModel: ViewModel() {
    private val notificationRepository = NotificationsRepositoryImpl
    private val createMissYouNotification = CreateMissYouNotification(notificationRepository)

    fun sendNotificationToTopicLove(context: Context, notificationData: NotificationData) {
        createMissYouNotification.invoke(context, notificationData)
    }

    fun subscribeToLoveTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM", "Successfully subscribed to topic")
                } else {
                    Log.e("FCM", "Failed to subscribe to topic")
                }
            }
    }

}