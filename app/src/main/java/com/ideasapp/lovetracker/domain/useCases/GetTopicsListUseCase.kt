package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.TopicItem
import com.ideasapp.lovetracker.domain.repository.TopicsRepository

class GetTopicsListUseCase (private val repository: com.ideasapp.lovetracker.domain.repository.TopicsRepository) {
    operator fun invoke(): com.ideasapp.lovetracker.domain.entity.TopicItem {
        return repository.getTopicItem()
    }
}