package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.SexItem
import com.ideasapp.lovetracker.domain.entity.SleepoverItem
import com.ideasapp.lovetracker.domain.repository.SleepoverRepository

class AddSleepoverItemUseCase (private val repository: SleepoverRepository) {
    operator fun invoke(sleepoverItem: SleepoverItem) {
        repository.addSleepoverItem(sleepoverItem)
    }
}