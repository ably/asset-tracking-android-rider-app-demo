package com.ably.tracking.demo.publisher.ably

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.common.NotificationProvider
import com.ably.tracking.publisher.PublisherNotificationProvider

class PublisherNotificationProviderAdapter(private val notificationProvider: NotificationProvider) :
    PublisherNotificationProvider {

    override fun getNotification(): Notification = notificationProvider.notification
}
