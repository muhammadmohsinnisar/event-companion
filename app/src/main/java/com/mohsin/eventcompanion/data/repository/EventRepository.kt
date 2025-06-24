package com.mohsin.eventcompanion.data.repository

import com.mohsin.eventcompanion.domain.model.Event
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class EventRepository {
    private val client = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    @Throws(IOException::class)
    suspend fun fetchEventFromUrl(url: String): Event {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseBody = response.body.string()
                ?: throw IOException("Empty response body")

            return json.decodeFromString(Event.serializer(), responseBody)
        }
    }
}