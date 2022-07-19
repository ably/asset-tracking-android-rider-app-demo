package com.ably.tracking.demo.publisher.ably.log

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

class DefaultFileManager(private val context: Context) : FileManager {

    override fun createFile(directoryName: String, fileName: String): File {
        ensureDirectoryExists(directoryName)

        val file = File(getDirectoryPath(directoryName) + fileName)
        if (!file.exists()) {
            file.createNewFile()
        }
        return file
    }

    private fun ensureDirectoryExists(directoryName: String) {
        val directory = File(getDirectoryPath(directoryName))
        if (!directory.exists()) {
            directory.mkdir()
        }
    }

    override fun getFiles(directoryName: String): List<Uri> {
        val directoryPath = getDirectoryPath(directoryName)
        return File(directoryPath).listFiles()?.map {
            FileProvider.getUriForFile(context, "com.ably.tracking.demo.publisher.fileprovider", it)
        } ?: emptyList()
    }

    override fun removeDirectory(directoryName: String) {
        val directoryPath = getDirectoryPath(directoryName)
        val directory = File(directoryPath)
        directory.deleteRecursively()
        directory.listFiles()
    }

    private fun File.deleteRecursively() {
        if (isDirectory) {
            listFiles()?.forEach {
                it.deleteRecursively()
            }
        }

        delete()
    }

    private fun getDirectoryPath(directoryName: String) = context.filesDir.path + "/$directoryName/"

}
