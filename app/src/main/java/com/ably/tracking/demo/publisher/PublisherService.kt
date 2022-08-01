package com.ably.tracking.demo.publisher

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.ably.tracking.demo.publisher.common.NotificationProvider
import com.ably.tracking.demo.publisher.domain.OrderInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class PublisherService : Service() {
    // SupervisorJob() is used to keep the scope working after any of its children fail
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val notificationProvider: NotificationProvider by inject()

    private val orderInteractor: OrderInteractor by inject()

    override fun onBind(intent: Intent?): IBinder = Binder()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(notificationProvider.notificationId, notificationProvider.notification)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        scope.launch { orderInteractor.disconnect() }
        super.onDestroy()
    }
}
