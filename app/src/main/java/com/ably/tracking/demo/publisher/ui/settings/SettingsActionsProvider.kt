package com.ably.tracking.demo.publisher.ui.settings

import android.os.Process
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.demo.publisher.ably.log.LocationLogger
import com.ably.tracking.demo.publisher.ui.navigation.Navigator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SettingsActionsProvider(
    private val assetTracker: AssetTracker,
    private val locationLogger: LocationLogger,
    private val navigator: Navigator
) {

    fun startSession() {
        GlobalScope.launch {
            assetTracker.connect()
        }
    }

    fun closeSession() {
        GlobalScope.launch {
            assetTracker.disconnect()
        }
    }

    fun exportLogs() {
        val logFiles = locationLogger.getLogFiles()
        navigator.share(R.string.log_share_header, logFiles)
    }

    fun removeLogs() {
        locationLogger.removeLogFiles()
    }

    fun restartApplication() {
        Process.killProcess(Process.myPid())
    }
}
