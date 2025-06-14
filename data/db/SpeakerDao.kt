package com.mohsin.eventcompanion.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SpeakerDao {

    @Query("SELECT * FROM speakers")
    fun getAllSpeakers(): Flow<List<SpeakerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeakers(speakers: List<SpeakerEntity>)
}
