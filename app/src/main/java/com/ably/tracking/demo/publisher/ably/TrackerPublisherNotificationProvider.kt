package com.ably.tracking.demo.publisher.ably

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.publisher.PublisherNotificationProvider

class TrackerPublisherNotificationProvider(private val context: Context) :
    PublisherNotificationProvider {

    override fun getNotification(): Notification {
        createNotificationChannel()
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance)
            channel.description = NOTIFICATION_CHANNEL_DESCRIPTION
            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object{
        private const val NOTIFICATION_CHANNEL_ID="trackingPublisherChannel"
        private const val NOTIFICATION_CHANNEL_NAME="Tracking Notifications"
        private const val NOTIFICATION_CHANNEL_DESCRIPTION="Notifications about tracking service running"
    }
}
