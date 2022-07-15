package com.ably.tracking.demo.publisher.ably

import android.content.Context
import java.io.File
import java.io.FileWriter

class LogFileWriterImpl(private val context: Context) : LogFileWriter {

    private var fileWriter: FileWriter? = null

    override val isReady: Boolean
        get() = fileWriter != null

    override fun prepare(directory: String, fileName: String) {
        val directoryPath = context.filesDir.path + "/$directory/"
        createDirectory(directoryPath)

        val file = createFile(directoryPath, fileName)

        fileWriter = FileWriter(file)
    }

    private fun createFile(directoryPath: String, fileName: String): File {
        val file = File(directoryPath + fileName)
        if (!file.exists()) {
            file.createNewFile()
        }
        return file
    }

    private fun createDirectory(directoryPath: String) {
        val directory = File(directoryPath)
        if (!directory.exists()) {
            val mkdir = directory.mkdir()
            println(mkdir)
        }
    }

    override fun writeLine(line: String) {
        fileWriter!!.appendLine(line)
        fileWriter!!.flush()
    }

    override fun close() {
        fileWriter?.close()
    }
}
