package com.ably.tracking.demo.publisher.ably

class FakeFileWriter : FileWriter {

    var fileName: String? = null
        private set

    override val isReady: Boolean = fileName != null

    val fileLines = mutableListOf<String>()

    override fun prepare(fileName: String) {
        this.fileName = fileName
    }

    override fun write(line: String) {
        fileLines.add(line)
    }
}
