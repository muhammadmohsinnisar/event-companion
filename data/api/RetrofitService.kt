package com.mohsin.eventcompanion.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class SessionDto(
    val id: Int,
    val title: String,
    val speakerId: Int,
    val time: String
)

data class SpeakerDto(
    val id: Int,
    val name: String,
    val bio: String
)

data class TicketDto(
    val qrCodeData: String
)

interface EventApiService {

    @GET("sessions.json")
    suspend fun getSessions(): List<SessionDto>

    @GET("speakers.json")
    suspend fun getSpeakers(): List<SpeakerDto>

    @GET("ticket.json")
    suspend fun getTicket(): TicketDto
}

object RetrofitService {
    private const val BASE_URL = "https://muhammadmohsinnisar.github.io/eventcompanion-mock-api/"

    val api: EventApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EventApiService::class.java)
    }
}
