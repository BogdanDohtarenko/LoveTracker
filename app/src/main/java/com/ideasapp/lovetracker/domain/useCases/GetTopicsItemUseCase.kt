package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.TopicItem
import com.ideasapp.lovetracker.domain.repository.TopicsRepository

class GetTopicsItemUseCase (private val repository: TopicsRepository) {
    operator fun invoke(): List<TopicItem> {
        return repository.getTopicItemTasksList()
    }
}