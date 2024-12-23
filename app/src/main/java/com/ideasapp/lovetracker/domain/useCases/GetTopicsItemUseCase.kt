package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.TopicItem
import com.ideasapp.lovetracker.domain.repository.TopicsRepository

class GetTopicsItemUseCase (private val repository: com.ideasapp.lovetracker.domain.repository.TopicsRepository) {
    operator fun invoke(): List<com.ideasapp.lovetracker.domain.entity.TopicItem> {
        return repository.getTopicItemTasksList()
    }
}