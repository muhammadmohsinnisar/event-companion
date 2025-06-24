package com.mohsin.eventcompanion.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SessionWithSpeakerDto(
    val id: Int,
    val title: String,
    val description: String,
    val startTime: String,
    val endTime: String,
    val eventId: Int,
    val speaker: SpeakerDto
)
