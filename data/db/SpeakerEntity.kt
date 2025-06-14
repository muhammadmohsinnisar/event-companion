package com.mohsin.eventcompanion.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "speakers")
data class SpeakerEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val bio: String
)
