package com.ably.tracking.demo.publisher.data.ably

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.ably.tracking.demo.publisher.R

class NotificationProvider(private val context: Context) {

    val notification: Notification by lazy {
        buildNotification(context)
    }

    val notificationId = NOTIFICATION_ID

    private fun buildNotification(context: Context): Notification {
        createNotificationChannel(context)
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Rider app")
            .setContentText("Tracking running in the background")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                importance
            )
            channel.description = NOTIFICATION_CHANNEL_DESCRIPTION
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "trackingPublisherChannel"
        private const val NOTIFICATION_CHANNEL_NAME = "Tracking Notifications"
        private const val NOTIFICATION_CHANNEL_DESCRIPTION =
            "Notifications about tracking service running"

        private const val NOTIFICATION_ID = 97852
    }
}
