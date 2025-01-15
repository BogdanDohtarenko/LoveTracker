package com.ideasapp.lovetracker.domain.repository

import android.content.Context
import com.ideasapp.lovetracker.domain.entity.NotificationData

interface NotificationsRepository {
    fun createMissYouNotification(context: Context, notificationData: NotificationData)
}