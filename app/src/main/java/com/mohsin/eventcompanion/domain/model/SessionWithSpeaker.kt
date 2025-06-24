package com.mohsin.eventcompanion.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SessionWithSpeaker(
    val id: Int,
    val title: String,
    val description: String,
    val startTime: String,
    val endTime: String,
    val eventId: Int,
    val speaker: Speaker,
    var isFavorite: Boolean = false
)
