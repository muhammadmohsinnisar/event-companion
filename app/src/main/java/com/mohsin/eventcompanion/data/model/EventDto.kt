package com.mohsin.eventcompanion.data.model

import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val id: Int,
    val name: String,
    val description: String,
    val date: String,
    val location: String,
    val sessions: List<SessionWithSpeakerDto>
)
