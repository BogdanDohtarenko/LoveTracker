package com.ideasapp.lovetracker.domain.repository

import com.ideasapp.lovetracker.domain.entity.TopicItem

interface TopicsRepository {
    fun addTopicItem(topicItem: com.ideasapp.lovetracker.domain.entity.TopicItem)
    fun deleteTopicItem(topicItem: com.ideasapp.lovetracker.domain.entity.TopicItem)
    fun getTopicItem(): com.ideasapp.lovetracker.domain.entity.TopicItem
    fun getTopicItemTasksList(): List<com.ideasapp.lovetracker.domain.entity.TopicItem>
}