package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.SexItem
import com.ideasapp.lovetracker.domain.repository.SexRepository

class GetSexItemUseCase(private val repository: com.ideasapp.lovetracker.domain.repository.SexRepository) {
    operator fun invoke(): com.ideasapp.lovetracker.domain.entity.SexItem {
        return repository.getSexItem()
    }
}