package com.ably.tracking.demo.publisher.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.ably.tracking.demo.publisher.PublisherService
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.common.PermissionHelper
import com.ably.tracking.demo.publisher.ui.debug.SettingsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainScreen(viewModel,::openSettings) }
        ContextCompat.startForegroundService(this, Intent(this, PublisherService::class.java))

        PermissionHelper.ensureLocationPermission(this, this::showPermissionRequiredDialog) {
            viewModel.onLocationPermissionGranted()
        }
    }

    private fun showPermissionRequiredDialog() {
        showOkDialog(
            title = R.string.location_permission_denied_dialog_title,
            message = R.string.location_permission_denied_dialog_message,
            onOk = this::finish
        )
    }

    private fun showOkDialog(@StringRes title: Int, @StringRes message: Int, onOk: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { _, _ -> onOk() }
            .setCancelable(false)
            .show()
    }

    private fun openSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
}
