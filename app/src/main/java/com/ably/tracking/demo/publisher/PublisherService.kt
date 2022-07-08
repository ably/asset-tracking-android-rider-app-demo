package com.ably.tracking.demo.publisher

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.demo.publisher.common.NotificationProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class PublisherService : Service() {
    // SupervisorJob() is used to keep the scope working after any of its children fail
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val binder = Binder()

    private val notificationProvider: NotificationProvider by inject()

    private val assetTracker: AssetTracker by inject()

    inner class Binder : android.os.Binder() {
        fun getService(): PublisherService = this@PublisherService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(notificationProvider.notificationId, notificationProvider.notification)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        scope.launch { assetTracker.disconnect() }
        super.onDestroy()
    }
}
