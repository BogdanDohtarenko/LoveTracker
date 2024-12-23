package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.SleepoverItem
import com.ideasapp.lovetracker.domain.repository.SleepoverRepository

class GetSleepoverItemUseCase (private val repository: com.ideasapp.lovetracker.domain.repository.SleepoverRepository) {
    operator fun invoke(): com.ideasapp.lovetracker.domain.entity.SleepoverItem {
       return repository.getSleepoverItem()
    }
}