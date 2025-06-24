package com.mohsin.eventcompanion.data.repository

import android.content.Context
import androidx.room.Room
import com.mohsin.eventcompanion.data.local.AppDatabase
import com.mohsin.eventcompanion.data.local.EventEntity
import com.mohsin.eventcompanion.domain.model.Event
import kotlinx.serialization.json.Json

class LocalEventRepository(context: Context) {
    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "event_db"
    ).build()

    private val dao = db.eventDao()
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun saveEvent(event: Event) {
        val jsonString = json.encodeToString(event)
        dao.insertEvent(EventEntity(
            event.id,
            name = event.name,
            description = event.description,
            date = event.date,
            location = event.location,
            json = jsonString
        ))
    }

    suspend fun getAllEvents(): List<Event> {
        return dao.getAllEvents().map {
            json.decodeFromString<Event>(it.json)
        }
    }
}
