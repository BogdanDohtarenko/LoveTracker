package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.SexItem
import com.ideasapp.lovetracker.domain.repository.NotificationsRepository
import com.ideasapp.lovetracker.domain.repository.SexRepository

class AddSexItemUseCase(private val repository: SexRepository) {
    operator fun invoke(sexItem: SexItem) {
        repository.addSexItem(sexItem)
    }
}