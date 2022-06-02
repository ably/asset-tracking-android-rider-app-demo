package com.ably.tracking.demo.publisher.ably

import android.util.Log
import com.ably.tracking.logging.LogHandler
import com.ably.tracking.logging.LogLevel

class TrackerLogHandler : LogHandler {
    override fun logMessage(level: LogLevel, message: String, throwable: Throwable?) {
        Log.d("AssetTracker", "$message $throwable")
    }
}
