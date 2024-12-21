package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.SexItem
import com.ideasapp.lovetracker.domain.repository.SexRepository

class GetSexTasksListUseCase (private val repository: SexRepository) {
    operator fun invoke(): List<SexItem> {
        return repository.getSexTasksList()
    }
}