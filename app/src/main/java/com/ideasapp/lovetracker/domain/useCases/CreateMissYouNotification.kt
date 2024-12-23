package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.repository.NotificationsRepository

class CreateMissYouNotification(private val repository: com.ideasapp.lovetracker.domain.repository.NotificationsRepository) {
    operator fun invoke() {
        repository.createMissYouNotification()
    }

}