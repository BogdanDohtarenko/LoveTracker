package com.ideasapp.lovetracker.domain.useCases

import com.ideasapp.lovetracker.domain.entity.TopicItem
import com.ideasapp.lovetracker.domain.entity.WalkItem
import com.ideasapp.lovetracker.domain.repository.TopicsRepository
import com.ideasapp.lovetracker.domain.repository.WalksRepository

class DeleteTopicsItemUseCase(private val repository: TopicsRepository) {
    operator fun invoke(topicItem: TopicItem) {
        repository.deleteTopicItem(topicItem)
    }
}