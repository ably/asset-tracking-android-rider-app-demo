package com.ably.tracking.demo.publisher.data.ably

import android.net.Uri
import com.ably.tracking.demo.publisher.data.ably.log.FileManager
import java.io.File

class FakeFileManager : FileManager {

    private val filePaths = mutableListOf<String>()

    override fun createFile(directoryName: String, fileName: String): File {
        val path = "$directoryName/$fileName"
        filePaths.add(path)
        return File(path)
    }

    override fun getFiles(directoryName: String): List<Uri> =
        filePaths.map { Uri.parse(it) }

    override fun removeDirectory(directoryName: String) {
        filePaths.removeAll { it.contains(directoryName) }
    }
}
