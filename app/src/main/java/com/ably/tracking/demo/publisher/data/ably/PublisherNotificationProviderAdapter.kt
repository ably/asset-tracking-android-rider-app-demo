package com.ably.tracking.demo.publisher.data.ably

import android.app.Notification
import com.ably.tracking.demo.publisher.common.NotificationProvider
import com.ably.tracking.publisher.PublisherNotificationProvider

class PublisherNotificationProviderAdapter(private val notificationProvider: NotificationProvider) :
    PublisherNotificationProvider {

    override fun getNotification(): Notification = notificationProvider.notification
}
