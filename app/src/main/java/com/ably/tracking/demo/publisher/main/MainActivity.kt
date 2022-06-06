package com.ably.tracking.demo.publisher.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.demo.publisher.common.PermissionHelper
import com.ably.tracking.demo.publisher.common.ViewModelProviderFactory
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelProviderFactory(this))[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainScreen(viewModel) }

        PermissionHelper.ensureLocationPermission(this) {
            viewModel.beginTracking()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.finishTracking()
    }
}
