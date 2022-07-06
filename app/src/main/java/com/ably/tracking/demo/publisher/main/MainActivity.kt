package com.ably.tracking.demo.publisher.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import com.ably.tracking.demo.publisher.PublisherService
import com.ably.tracking.demo.publisher.common.PermissionHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainScreen(viewModel) }
        ContextCompat.startForegroundService(this, Intent(this, PublisherService::class.java))
    }

    override fun onResume() {
        super.onResume()
        PermissionHelper.ensureLocationPermission(this) {
            viewModel.onResumeWithLocationPermission()
        }
    }
}
