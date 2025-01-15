package com.ideasapp.lovetracker.domain.useCases

import android.content.Context
import com.ideasapp.lovetracker.domain.entity.NotificationData
import com.ideasapp.lovetracker.domain.repository.NotificationsRepository

class CreateMissYouNotification(private val repository: com.ideasapp.lovetracker.domain.repository.NotificationsRepository) {
    operator fun invoke(context: Context, notificationData: NotificationData) {
        repository.createMissYouNotification(context, notificationData)
    }

}