package com.ably.tracking.demo.publisher.ably

import com.ably.tracking.Location
import com.ably.tracking.LocationUpdate
import com.ably.tracking.publisher.LocationHistoryData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LocationLogger(private val fileWriter: LogFileWriter) {

    companion object {
        private const val LOG_TIME_FORMATTER_PATTERN = "HH:mm:ss"
        private const val FILE_NAME_FORMATTER_PATTERN = "dd.MM_HH:mm:ss"
        private const val FILE_NAME_SUFFIX = "_location.log"
        private const val LOG_DIRECTORY = "riderLogs"
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
            val fileName = formatter.format(date) + FILE_NAME_SUFFIX

            fileWriter.prepare(LOG_DIRECTORY, fileName)
        }
    }

    private fun logLocation(location: Location) {
        val date = Date(location.time)
        fileWriter.writeLine(logTimeFormatter.format(date) + SPACE + location)
    }

    fun close() {
        fileWriter.close()
    }

    fun logLocationHistoryData(locationHistoryData: LocationHistoryData) {
        TODO("Not yet implemented")
    }
}
