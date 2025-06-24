package com.mohsin.eventcompanion.data.model

import com.mohsin.eventcompanion.domain.model.*

fun EventDto.toDomain(): Event = Event(
    id = id,
    name = name,
    description = description,
    date = date,
    location = location,
    sessions = sessions.map { it.toDomain() }
)

fun SessionWithSpeakerDto.toDomain(): SessionWithSpeaker = SessionWithSpeaker(
    id = id,
    title = title,
    description = description,
    startTime = startTime,
    endTime = endTime,
    eventId = eventId,
    speaker = speaker.toDomain()
)

fun SpeakerDto.toDomain(): Speaker = Speaker(
    id = id,
    name = name,
    bio = bio,
    eventId = eventId
)
