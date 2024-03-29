package com.ably.tracking.demo.publisher.data.ably.log

interface LogFileWriter {

    val isReady: Boolean

    fun prepare(directory: String, fileName: String)

    fun writeLine(line: String)

    fun close()
}
