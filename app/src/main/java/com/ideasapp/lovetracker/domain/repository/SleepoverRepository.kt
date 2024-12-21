package com.ideasapp.lovetracker.domain.repository

import com.ideasapp.lovetracker.domain.entity.SleepoverItem

interface SleepoverRepository {
    fun addSleepoverItem(sleepoverItem: SleepoverItem)
    fun deleteSleepoverItem(sleepoverItem: SleepoverItem)
    fun getSleepoverItem(): SleepoverItem
    fun getSleepoverTasksList(): List<SleepoverItem>
}