package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.SexItem
import com.ideasapp.lovetracker.domain.repository.SexRepository

class GetSexItemUseCase(private val repository: SexRepository) {
    operator fun invoke() {
        repository.getSexItem()
    }
}