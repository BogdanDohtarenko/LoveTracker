package com.ideasapp.lovetracker.presentation.models

import androidx.lifecycle.ViewModel
import com.ideasapp.lovetracker.data.NotificationsRepositoryImpl
import com.ideasapp.lovetracker.domain.useCases.CreateMissYouNotification

class MainViewModel: ViewModel() {
    private val notificationRepository = NotificationsRepositoryImpl
    private val createMissYouNotification = CreateMissYouNotification(notificationRepository)
    fun createMissYouNotification() {
        createMissYouNotification.invoke()
    }
}