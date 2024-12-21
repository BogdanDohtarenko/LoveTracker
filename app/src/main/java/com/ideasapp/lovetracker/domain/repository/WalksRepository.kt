package com.ideasapp.lovetracker.domain.repository

import com.ideasapp.lovetracker.domain.entity.WalkItem

interface WalksRepository {
    fun addWalkItem(walkItem: WalkItem)
    fun getWalkItem(): WalkItem
    fun getWalkTasksList(): List<WalkItem>
    fun deleteWalkItem(walkItem: WalkItem)
}