package com.ably.tracking.demo.publisher.ably

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.publisher.PublisherNotificationProvider

class TrackerPublisherNotificationProvider(private val context: Context) :
    PublisherNotificationProvider {
    override fun getNotification(): Notification {
        return NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }
}