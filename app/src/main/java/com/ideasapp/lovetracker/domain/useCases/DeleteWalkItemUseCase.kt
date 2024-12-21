package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.WalkItem
import com.ideasapp.lovetracker.domain.repository.WalksRepository

class DeleteWalkItemUseCase(private val repository: WalksRepository) {
    operator fun invoke(walkItem: WalkItem) {
        repository.deleteWalkItem(walkItem)
    }
}