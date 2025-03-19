package com.rdan.footballgather.ui.screens.gather.timer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.rdan.footballgather.R
import com.rdan.footballgather.ui.MainActivity

class NotificationHandler {
    private companion object {
        const val NOTIFICATION_CHANNEL_ID = "TIMER_NOTIFICATION_CHANNEL"
        const val NOTIFICATION_CHANNEL_NAME = "Timer Notifications"
        const val NOTIFICATION_ID = 1
    }

    fun showTimerFinishedNotification(context: Context) {
        val notificationManager = createNotificationManager(context)
        if (!notificationManager.areNotificationsEnabled()) {
            return
        }
        createNotificationChannel(notificationManager)
        notificationManager.notify(
            NOTIFICATION_ID, createNotification(context)
        )
    }

    private fun createNotificationManager(
        context: Context
    ): NotificationManager {
        return context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
    }

    private fun createNotificationChannel(
        notificationManager: NotificationManager
    ) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(
        context: Context
    ): Notification {
        val intent = Intent(context, MainActivity::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        return  NotificationCompat.Builder(
            context, NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_content))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
    }
}