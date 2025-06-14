package com.mohsin.eventcompanion.data.repository

import com.mohsin.eventcompanion.data.api.RetrofitService
import com.mohsin.eventcompanion.data.db.*
import kotlinx.coroutines.flow.Flow

class EventRepository(
    private val sessionDao: SessionDao,
    private val speakerDao: SpeakerDao
) {

    val sessionsFlow: Flow<List<SessionEntity>> = sessionDao.getAllSessions()

    suspend fun refreshSessions() {
        val remoteSessions = RetrofitService.api.getSessions()
        val entities = remoteSessions.map { session ->
            SessionEntity(
                id = session.id,
                title = session.title,
                speakerId = session.speakerId,
                time = session.time
            )
        }
        sessionDao.insertSessions(entities)
    }

    suspend fun updateSession(session: SessionEntity) {
        sessionDao.updateSession(session)
    }

    val speakersFlow: Flow<List<SpeakerEntity>> = speakerDao.getAllSpeakers()

    suspend fun refreshSpeakers() {
        val remoteSpeakers = RetrofitService.api.getSpeakers()
        val entities = remoteSpeakers.map { speaker ->
            SpeakerEntity(
                id = speaker.id,
                name = speaker.name,
                bio = speaker.bio
            )
        }
        speakerDao.insertSpeakers(entities)
    }
}
