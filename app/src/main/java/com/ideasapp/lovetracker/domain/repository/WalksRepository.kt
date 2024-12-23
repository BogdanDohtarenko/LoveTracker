package com.ideasapp.lovetracker.domain.repository

import com.ideasapp.lovetracker.domain.entity.WalkItem

interface WalksRepository {
    fun addWalkItem(walkItem: com.ideasapp.lovetracker.domain.entity.WalkItem)
    fun getWalkItem(): com.ideasapp.lovetracker.domain.entity.WalkItem
    fun getWalkTasksList(): List<com.ideasapp.lovetracker.domain.entity.WalkItem>
    fun deleteWalkItem(walkItem: com.ideasapp.lovetracker.domain.entity.WalkItem)
}