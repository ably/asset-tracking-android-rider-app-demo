package com.ably.tracking.demo.publisher.ui.settings

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Process
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.demo.publisher.ably.log.LocationLogger
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SettingsActionsProvider(
    private val assetTracker: AssetTracker,
    private val locationLogger: LocationLogger,
    private val activity: Activity
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
        val logFiles = ArrayList(locationLogger.getLogFiles())
        share(logFiles)
    }

    private fun share(logFiles: ArrayList<Uri>) {
        val header = activity.getString(R.string.log_share_header)
        val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "text/html"
        intent.putExtra(Intent.EXTRA_SUBJECT, header)
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, logFiles)

        val title = activity.getString(R.string.log_share_dialog_title)
        activity.startActivity(Intent.createChooser(intent, title))
    }

    fun removeLogs() {
        locationLogger.removeLogFiles()
    }

    fun restartApplication() {
        Process.killProcess(Process.myPid())
    }
}
