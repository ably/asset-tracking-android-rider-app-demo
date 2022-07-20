package com.ably.tracking.demo.publisher.ably.log

import java.io.FileWriter

class DefaultLogFileWriter(private val fileManager: DefaultFileManager) : LogFileWriter {

    private var fileWriter: FileWriter? = null

    override val isReady: Boolean
        get() = fileWriter != null

    override fun prepare(directory: String, fileName: String) {
        val file = fileManager.createFile(directory, fileName)
        fileWriter = FileWriter(file)
    }

    override fun writeLine(line: String) {
        fileWriter!!.appendLine(line)
        fileWriter!!.flush()
    }

    override fun close() {
        fileWriter?.close()
    }
}
