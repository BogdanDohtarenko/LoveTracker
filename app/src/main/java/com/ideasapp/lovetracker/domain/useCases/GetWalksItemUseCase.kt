package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.WalkItem
import com.ideasapp.lovetracker.domain.repository.WalksRepository

class GetWalksItemUseCase (private val repository: WalksRepository) {
    operator fun invoke(): WalkItem {
        return repository.getWalkItem()
    }
}