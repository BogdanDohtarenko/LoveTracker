package com.ideasapp.lovetracker.domain.repository

import com.ideasapp.lovetracker.domain.entity.SexItem

interface SexRepository {
    fun addSexItem(sexItem: SexItem)
    fun deleteSexItem(sexItem: SexItem)
    fun getSexItem(): SexItem
    fun getSexTasksList(): List<SexItem>
}