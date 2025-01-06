package com.ideasapp.lovetracker.domain.useCases

import android.content.Context
import com.ideasapp.lovetracker.domain.repository.NotificationsRepository

class CreateMissYouNotification(private val repository: com.ideasapp.lovetracker.domain.repository.NotificationsRepository) {
    operator fun invoke(context: Context) {
        repository.createMissYouNotification(context)
    }

}