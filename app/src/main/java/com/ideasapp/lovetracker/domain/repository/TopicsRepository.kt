package com.ideasapp.lovetracker.domain.repository

import com.ideasapp.lovetracker.domain.entity.TopicItem

interface TopicsRepository {
    fun addTopicItem(topicItem: TopicItem)
    fun deleteTopicItem(topicItem: TopicItem)
    fun getTopicItem(): TopicItem
    fun getTopicItemTasksList(): List<TopicItem>
}