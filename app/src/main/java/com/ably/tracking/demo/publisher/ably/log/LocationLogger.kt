package com.ably.tracking.demo.publisher.ably.log

import com.ably.tracking.Location
import com.ably.tracking.LocationUpdate
import com.ably.tracking.publisher.LocationHistoryData
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LocationLogger(
    private val fileWriter: LogFileWriter,
    private val fileManager: FileManager,
    private val gson: Gson
) {

    companion object {
        private const val LOG_TIME_FORMATTER_PATTERN = "HH:mm:ss"
        private const val FILE_NAME_FORMATTER_PATTERN = "dd_MM_HH:mm:ss"
        private const val LOG_FILE_NAME_SUFFIX = "_location.log"
        private const val HISTORY_FILE_NAME_SUFFIX = "_history.log"
        private const val LOG_DIRECTORY = "riderLogs"
        private const val SPACE = " "
    }

    private var sessionStart: Long = 0

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
            sessionStart = location.time
            val fileName = sessionStart.toFileName() + LOG_FILE_NAME_SUFFIX

            fileWriter.prepare(LOG_DIRECTORY, fileName)
        }
    }

    private fun logLocation(location: Location) {
        val date = Date(location.time)
        fileWriter.writeLine(logTimeFormatter.format(date) + SPACE + location)
    }

    fun logLocationHistoryDataAndClose(locationHistoryData: LocationHistoryData) {
        //close locations logs writer
        closeFileWriter()

        //prepare location history writer
        val fileName = sessionStart.toFileName() + HISTORY_FILE_NAME_SUFFIX
        fileWriter.prepare(LOG_DIRECTORY, fileName)
        val locationHistoryDataJson = gson.toJson(locationHistoryData)
        fileWriter.writeLine(locationHistoryDataJson)
        closeFileWriter()
    }

    private fun closeFileWriter() {
        fileWriter.close()
    }

    private fun Long.toFileName(): String {
        val formatter = SimpleDateFormat(FILE_NAME_FORMATTER_PATTERN, Locale.getDefault())
        val date = Date(this)
        return formatter.format(date)
    }

    fun getLogFiles() = fileManager.getFiles(LOG_DIRECTORY)

    fun removeLogFiles() {
        fileManager.removeDirectory(LOG_DIRECTORY)
    }
}
