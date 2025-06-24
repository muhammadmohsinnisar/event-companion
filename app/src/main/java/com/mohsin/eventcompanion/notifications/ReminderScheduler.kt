package com.mohsin.eventcompanion.notifications

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.work.*
import com.mohsin.eventcompanion.domain.model.SessionWithSpeaker
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
fun scheduleSessionReminder(context: Context, session: SessionWithSpeaker) {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    try {
        val sessionStart = LocalDateTime.parse(session.startTime, formatter)
        val now = LocalDateTime.now()
        val delay = Duration.between(now, sessionStart.minusMinutes(10)).toMillis()

        if (delay > 0) {
            val data = workDataOf("title" to session.title)

            val request = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .build()

            WorkManager.getInstance(context).enqueue(request)
        } else {
            Toast.makeText(context, "Session is too soon or already started.", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
