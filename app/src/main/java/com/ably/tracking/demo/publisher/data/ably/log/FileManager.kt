package com.ably.tracking.demo.publisher.data.ably.log

import android.net.Uri
import java.io.File

interface FileManager {
    fun createFile(directoryName: String, fileName: String): File
    fun getFiles(directoryName: String): List<Uri>
    fun removeDirectory(directoryName: String)
}
