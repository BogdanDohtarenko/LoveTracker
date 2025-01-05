package com.ideasapp.lovetracker.domain.repository

import com.ideasapp.lovetracker.domain.entity.SleepoverItem

interface SleepoverRepository {
    fun addSleepoverItem(sleepoverItem: com.ideasapp.lovetracker.domain.entity.SleepoverItem)
    fun deleteSleepoverItem(sleepoverItem: com.ideasapp.lovetracker.domain.entity.SleepoverItem)
    fun getSleepoverItem(): com.ideasapp.lovetracker.domain.entity.SleepoverItem
    fun getSleepoverTasksList(): List<com.ideasapp.lovetracker.domain.entity.SleepoverItem>
}