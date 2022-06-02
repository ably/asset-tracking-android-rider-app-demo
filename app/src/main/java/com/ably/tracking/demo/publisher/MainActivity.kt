package com.ably.tracking.demo.publisher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kotlinx.coroutines.runBlocking
import java.util.*

class MainActivity : ComponentActivity() {

    private val assetTracker = AssetTracker.build()

    private val clientId: String = UUID.randomUUID().toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainScreen() }

        PermissionHelper.ensureLocationPermission(this) {
            assetTracker.connect(this, clientId)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        runBlocking {
            assetTracker.disconnect()
        }
    }
}
