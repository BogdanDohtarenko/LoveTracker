package com.ideasapp.lovetracker.domain.repository

import android.content.Context

interface NotificationsRepository {
    fun createMissYouNotification(context: Context)
}