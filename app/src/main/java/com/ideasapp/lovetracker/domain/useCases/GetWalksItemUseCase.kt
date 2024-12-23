package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.WalkItem
import com.ideasapp.lovetracker.domain.repository.WalksRepository

class GetWalksItemUseCase (private val repository: com.ideasapp.lovetracker.domain.repository.WalksRepository) {
    operator fun invoke(): com.ideasapp.lovetracker.domain.entity.WalkItem {
        return repository.getWalkItem()
    }
}