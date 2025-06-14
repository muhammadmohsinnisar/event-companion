package com.mohsin.eventcompanion.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Query("SELECT * FROM sessions")
    fun getAllSessions(): Flow<List<SessionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSessions(sessions: List<SessionEntity>)

    @Update
    suspend fun updateSession(session: SessionEntity)
}
