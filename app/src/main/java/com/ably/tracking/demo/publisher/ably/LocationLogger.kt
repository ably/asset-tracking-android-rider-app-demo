package com.ably.tracking.demo.publisher.ably

import com.ably.tracking.Location
import com.ably.tracking.LocationUpdate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LocationLogger(private val fileWriter: FileWriter) {

    companion object {
        private const val LOG_TIME_FORMATTER_PATTERN = "HH:mm:ss"
        private const val FILE_NAME_FORMATTER_PATTERN = "dd.MM_HH:mm:ss"
        private const val FILE_NAME_SUFFIX = "_location.log"
        private const val SPACE = " "
    }

    private val logTimeFormatter by lazy {
        SimpleDateFormat(LOG_TIME_FORMATTER_PATTERN, Locale.getDefault())
    }

    fun logLocationUpdate(locationUpdate: LocationUpdate) {
        val location = locationUpdate.location
        ensureLogFileExists(location)
        logLocation(location)
    }

    private fun ensureLogFileExists(location: Location) {
        if (!fileWriter.isReady) {
            val formatter = SimpleDateFormat(FILE_NAME_FORMATTER_PATTERN, Locale.getDefault())
            val date = Date(location.time)
            fileWriter.prepare(formatter.format(date) + FILE_NAME_SUFFIX)
        }
    }

    private fun logLocation(location: Location) {
        val date = Date(location.time)
        fileWriter.write(logTimeFormatter.format(date) + SPACE + location)
    }
}
