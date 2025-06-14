package com.mohsin.eventcompanion.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val speakerId: Int,
    val time: String,
    val isFavorite: Boolean = false
)
