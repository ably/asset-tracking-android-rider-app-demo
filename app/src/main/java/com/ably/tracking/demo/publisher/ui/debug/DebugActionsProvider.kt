package com.ably.tracking.demo.publisher.ui.debug

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Process
import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.demo.publisher.ably.log.LocationLogger
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DebugActionsProvider(
    private val assetTracker: AssetTracker,
    private val locationLogger: LocationLogger,
    private val activity: Activity,
    private val clientId: String
) {

    fun startSession() {
        GlobalScope.launch {
            assetTracker.connect(clientId)
        }
    }

    fun closeSession() {
        GlobalScope.launch {
            assetTracker.disconnect()
        }
    }

    fun exportLogs() {
        val logFiles = ArrayList(locationLogger.getLogFiles())
        share(logFiles)
    }

    private fun share(logFiles: ArrayList<Uri>) {
        val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "text/html"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Header")
        intent.putExtra(Intent.EXTRA_TEXT, "Body")
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, logFiles)

        activity.startActivity(Intent.createChooser(intent, "Share Files"))
    }

    fun removeLogs() {
        locationLogger.removeLogFiles()
    }

    fun restartApplication() {
        Process.killProcess(Process.myPid())
    }
}
