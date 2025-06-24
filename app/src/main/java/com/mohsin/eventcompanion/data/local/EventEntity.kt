package com.mohsin.eventcompanion.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohsin.eventcompanion.domain.model.Event
import kotlinx.serialization.json.Json

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val date: String,
    val location: String,
    val json: String
) {
    companion object {
        fun fromDomain(event: Event, json: Json = Json { ignoreUnknownKeys = true }): EventEntity {
            return EventEntity(
                id = event.id,
                name = event.name,
                description = event.description,
                date = event.date,
                location = event.location,
                json = json.encodeToString(Event.serializer(), event)
            )
        }
    }
}
