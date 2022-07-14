package com.ably.tracking.demo.publisher.ably

interface FileWriter {

    val isReady: Boolean

    fun prepare(fileName: String)

    fun write(line: String)
}
