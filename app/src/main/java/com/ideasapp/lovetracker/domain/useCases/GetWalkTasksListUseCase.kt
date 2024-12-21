package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.WalkItem
import com.ideasapp.lovetracker.domain.repository.WalksRepository

class GetWalkTasksListUseCase (private val repository: WalksRepository) {
    operator fun invoke(): List<WalkItem> {
        return repository.getWalkTasksList()
    }
}