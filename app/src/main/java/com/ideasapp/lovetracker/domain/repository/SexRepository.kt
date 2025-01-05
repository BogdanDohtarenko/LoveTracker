package com.ideasapp.lovetracker.domain.repository

import com.ideasapp.lovetracker.domain.entity.SexItem

interface SexRepository {
    fun addSexItem(sexItem: com.ideasapp.lovetracker.domain.entity.SexItem)
    fun deleteSexItem(sexItem: com.ideasapp.lovetracker.domain.entity.SexItem)
    fun getSexItem(): com.ideasapp.lovetracker.domain.entity.SexItem
    fun getSexTasksList(): List<com.ideasapp.lovetracker.domain.entity.SexItem>
}