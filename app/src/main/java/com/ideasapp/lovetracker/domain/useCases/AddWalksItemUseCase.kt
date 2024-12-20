package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.WalkItem
import com.ideasapp.lovetracker.domain.repository.WalksRepository

class AddWalksItemUseCase(private val repository: WalksRepository) {
    operator fun invoke(walkItem: WalkItem) {
        repository.addWalkItem(walkItem)
    }
}