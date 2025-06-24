package com.mohsin.eventcompanion.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SpeakerDto(
    val id: Int,
    val name: String,
    val bio: String,
    val eventId: Int
)
