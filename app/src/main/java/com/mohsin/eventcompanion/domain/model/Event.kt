package com.mohsin.eventcompanion.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: Int,
    val name: String,
    val description: String,
    val date: String,
    val location: String,
    val sessions: List<SessionWithSpeaker>
)
