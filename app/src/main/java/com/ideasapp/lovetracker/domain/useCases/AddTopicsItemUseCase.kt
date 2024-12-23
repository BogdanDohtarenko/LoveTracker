package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.TopicItem
import com.ideasapp.lovetracker.domain.repository.TopicsRepository

class AddTopicsItemUseCase(private val repository: com.ideasapp.lovetracker.domain.repository.TopicsRepository) {
    operator fun invoke(topicItem: com.ideasapp.lovetracker.domain.entity.TopicItem) {
        repository.addTopicItem(topicItem)
    }
}