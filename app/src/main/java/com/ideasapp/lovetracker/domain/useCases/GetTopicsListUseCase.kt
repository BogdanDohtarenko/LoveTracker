package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.TopicItem
import com.ideasapp.lovetracker.domain.repository.TopicsRepository

class GetTopicsListUseCase (private val repository: TopicsRepository) {
    operator fun invoke(): TopicItem {
        return repository.getTopicItem()
    }
}