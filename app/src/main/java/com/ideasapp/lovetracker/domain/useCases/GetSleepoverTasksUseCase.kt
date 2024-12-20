package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.SleepoverItem
import com.ideasapp.lovetracker.domain.repository.SleepoverRepository

class GetSleepoverTasksUseCase (private val repository: SleepoverRepository) {
    operator fun invoke(): List<SleepoverItem> {
        return repository.getSleepoverTasksList()
    }
}