package com.ably.tracking.demo.publisher.ably

class FakeLogFileWriter : LogFileWriter {

    var fileName: String? = null
        private set

    override val isReady: Boolean = fileName != null
    val fileLines = mutableListOf<String>()

    override fun prepare(directory: String, fileName: String) {
        this.fileName = fileName
    }

    override fun writeLine(line: String) {
        fileLines.add(line)
    }

    override fun close() {
        fileName = null
    }
}
