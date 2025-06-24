package com.mohsin.eventcompanion.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mohsin.eventcompanion.R

class ReminderWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val sessionTitle = inputData.getString("title") ?: return Result.failure()

        val builder = NotificationCompat.Builder(applicationContext, "session_reminder")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentText("Starts soon: $sessionTitle")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
        }

        return Result.success()
    }
}
