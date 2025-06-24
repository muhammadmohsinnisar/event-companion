package com.mohsin.eventcompanion.data.repository

import android.content.Context
import com.mohsin.eventcompanion.data.local.AppDatabase
import com.mohsin.eventcompanion.data.local.EventEntity
import kotlinx.serialization.json.Json
import com.mohsin.eventcompanion.domain.model.Event

class RoomEventRepository(context: Context) {
    private val eventDao = AppDatabase.getInstance(context).eventDao()
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun saveEvent(event: Event) {
        val jsonString = json.encodeToString(Event.serializer(), event)
        eventDao.insertEvent(
            EventEntity(
                id = event.id,
                name = event.name,
                description = event.description,
                date = event.date,
                location = event.location,
                json = jsonString
            )
        )
    }

    suspend fun getAllEvents(): List<Event> {
        return eventDao.getAllEvents().map {
            json.decodeFromString(Event.serializer(), it.json)
        }
    }

    suspend fun deleteEvent(event: Event) {
        val entity = EventEntity.fromDomain(event, json)
        eventDao.delete(entity)
    }
}
