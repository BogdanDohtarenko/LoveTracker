package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.SexItem
import com.ideasapp.lovetracker.domain.entity.SleepoverItem
import com.ideasapp.lovetracker.domain.repository.SexRepository
import com.ideasapp.lovetracker.domain.repository.SleepoverRepository

class DeleteSleepoverItemUseCase(private val repository: com.ideasapp.lovetracker.domain.repository.SleepoverRepository) {
    operator fun invoke(sleepoverItem: com.ideasapp.lovetracker.domain.entity.SleepoverItem) {
        repository.deleteSleepoverItem(sleepoverItem)
    }
}