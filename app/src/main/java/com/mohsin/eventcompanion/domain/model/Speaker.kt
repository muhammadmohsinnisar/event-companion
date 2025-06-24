package com.mohsin.eventcompanion.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Speaker(
    val id: Int,
    val name: String,
    val bio: String,
    val eventId: Int
)

